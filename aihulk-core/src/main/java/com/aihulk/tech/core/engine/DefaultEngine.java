package com.aihulk.tech.core.engine;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.MergeStrategy;
import com.aihulk.tech.common.constant.UnitType;
import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.action.InvokeMethod;
import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.core.action.StreamOutput;
import com.aihulk.tech.core.config.RuleEngineConfigHolder;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.exception.RuleEngineException;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.resource.entity.*;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author yibozhang
 * @date 2019/5/1 12:44
 */
@Slf4j
public class DefaultEngine implements Engine {

    private Resource resource;

    private volatile boolean inited = false;

    private static ResourceLoader resourceLoader;

    static {
        try {
            resourceLoader = RuleEngineConfigHolder.config().getResourceLoader().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EngineInitException("resourceLoader 初始化失败");
        }
    }

    @Override
    public void init(Integer bizId, String version) throws EngineInitException {
        resource = (Resource) resourceLoader.loadResource(bizId, version);
        inited = true;
    }

    @Override
    public DecisionResponse decision(DecisionRequest request) {
        DecisionResponse response = new DecisionResponse();
        //0.param check
        Preconditions.checkArgument(request.getChainId() != null && request.getChainId() > 0, "chainId 参数不合法");
        //1.check engine status
        if (!inited) throw new RuleEngineException(RuleEngineException.Code.ENGINE_NOT_INIT, "engine 尚未初始化完成");
        //2.extract features
        DecisionChain decisionChain = getDecisionChain(request.getChainId());
        //3.iterate and eval rules
        Iterator<BasicUnit> iterator = decisionChain.iterator();
        Map<String, Object> variables = response.getVariables();
        while (iterator.hasNext()) {
            UnitExecuteResponse unitDecisionResponse;
            BasicUnit basicUnit = iterator.next();
            if (basicUnit.getUnitType() == UnitType.EXECUTE_UNIT) {
                unitDecisionResponse = this.evalExecuteUnits(Collections.singletonList((ExecuteUnit) basicUnit));
            } else if (basicUnit.getUnitType() == UnitType.EXECUTE_UNIT_GROUP) {
                List<ExecuteUnit> executeUnits = ((ExecuteUnitGroup) basicUnit).getExecuteUnits();
                unitDecisionResponse = this.evalExecuteUnits(executeUnits);
            } else {
                throw new UnsupportedOperationException("unknown basic unit type");
            }
            response.getExecExecuteUnits().addAll(unitDecisionResponse.getExecExecuteUnits());
            response.getFireExecuteUnits().addAll(unitDecisionResponse.getFireExecuteUnits());
            //变量合并逻辑
            List<OutPut> outPuts = unitDecisionResponse.getOutPuts();
            for (OutPut outPut : outPuts) {
                String key = outPut.getKey();
                String objExp = outPut.getObjExp();
                //根据变量类型做转型
                DataType dataType = outPut.getDataType();
                Object value = dataType.cast(objExp);
                if (variables.containsKey(key)) {
                    Object oldVal = variables.get(key);
                    MergeStrategy chainMergeStrategy = outPut.getChainMergeStrategy();
                    variables.put(key, chainMergeStrategy.merge(oldVal, value));
                } else {
                    variables.put(key, value);
                }
            }
            response.setVariables(variables);
        }

        response.setStatus(RuleEngineException.Code.SUCCESS.getCode());
        response.setMsg("决策成功");
        return response;

    }

    @Getter
    @Setter
    private static class UnitExecuteResponse {

        private List<ExecuteUnit> fireExecuteUnits = Lists.newArrayList();

        private List<ExecuteUnit> execExecuteUnits = Lists.newArrayList();

        private List<OutPut> outPuts = Lists.newArrayList();
    }

    private UnitExecuteResponse evalExecuteUnits(List<ExecuteUnit> executeUnits) {
        UnitExecuteResponse response = new UnitExecuteResponse();
        List<ExecuteUnit> fireExecuteUnits = response.getFireExecuteUnits();
        List<ExecuteUnit> execRules = response.getExecExecuteUnits();
        Map<String, OutPut> outPutMap = Maps.newHashMap();
        for (ExecuteUnit executeUnit : executeUnits) {
            //run executeUnit express
            ExecuteUnit.ExecuteUnitResponse evalResult = executeUnit.exec();
            execRules.add(executeUnit);
            if (!evalResult.isFired())
                continue;
            else
                fireExecuteUnits.add(executeUnit);
            List<Action> actions = evalResult.getActions();
            if (actions == null) continue;
            for (Action action : actions) {
                if (action instanceof OutPut) {
                    //输出一个变量
                    handleOutput(outPutMap, (OutPut) action);
                } else if (action instanceof InvokeMethod) {
                    //调用一个本地方法
                    handleMethodInvoke((InvokeMethod) action);
                } else if (action instanceof StreamOutput) {
                    //向一个流中输出
                    handleStreamOutput((StreamOutput) action);
                }
            }
        }
        response.setOutPuts(new ArrayList<>(outPutMap.values()));
        return response;
    }

    private void handleStreamOutput(StreamOutput output) {
        Object value = output.getOutPutValue();
        try (OutputStream os = output.getOs()) {
            os.write(value.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            log.error("stream output exception.", e);
        }
    }

    private void handleMethodInvoke(InvokeMethod invokeMethod) {
        Method method = invokeMethod.getMethod();
        Object[] args = invokeMethod.getArgs();
        Class<?> clazz = invokeMethod.getClazz();
        try {
            Object o = clazz.newInstance();
            method.invoke(o, args);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("method invoke exception,className = {},methodName = {},args = {},e = {}",
                    clazz.getName(), method.getName(), Arrays.toString(args), e.getCause());
        }
    }

    private void handleOutput(Map<String, OutPut> outPutMap, OutPut outPut) {
        String key = outPut.getKey();
        if (outPutMap.containsKey(key)) {
            OutPut oldOutPut = outPutMap.get(key);
            //变量合并逻辑
            MergeStrategy mergeStrategy = outPut.getUnitMergeStrategy();
            outPutMap.put(key, (OutPut) mergeStrategy.merge(oldOutPut, outPut));
        } else {
            outPutMap.put(key, outPut);
        }
    }

    private DecisionChain getDecisionChain(Integer chainId) {
        Optional<DecisionChain> decisionUnitOp = resource.getDecisionChains().stream().filter(
                unit -> unit.getId().equals(chainId)).findAny();
        return decisionUnitOp.orElseThrow(() -> new IllegalArgumentException("不存在的决策单元 chainId = " + chainId));
    }
}

package com.aihulk.tech.core.engine;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.core.config.RuleEngineConfig;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.exception.RuleEngineException;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.resource.entity.*;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @ClassName DefaultEngine
 * @Description 默认的执行引擎
 * @Author yibozhang
 * @Date 2019/5/1 12:44
 * @Version 1.0
 */
@Slf4j
public class DefaultEngine implements Engine {

    private Resource resource;

    private volatile boolean inited = false;

    private static ResourceLoader resourceLoader;

    static {
        try {
            resourceLoader = RuleEngineConfig.getResourceLoader().newInstance();
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
        while (iterator.hasNext()) {
            BasicUnit basicUnit = iterator.next();
            if (basicUnit.getUnitType() == BasicUnit.UnitType.EXECUTE_UNIT) {
                this.evalExecuteUnits(Arrays.asList((ExecuteUnit) basicUnit), response);
            } else if (basicUnit.getUnitType() == BasicUnit.UnitType.EXECUTE_UNIT_GROUP) {
                List<ExecuteUnit> executeUnits = ((ExecuteUnitGroup) basicUnit).getExecuteUnits();
                this.evalExecuteUnits(executeUnits, response);
            } else {
                throw new UnsupportedOperationException("unknown basic unit type");
            }
        }

        response.setStatus(0);
        response.setMsg("决策成功");
        return response;

    }

    private DecisionResponse evalExecuteUnits(List<ExecuteUnit> executeUnits, DecisionResponse response) {
        List<ExecuteUnit> fireExecuteUnits = response.getFireExecuteUnits();
        List<ExecuteUnit> execRules = response.getExecExecuteUnits();
        Map<String, Object> variables = response.getVariables();
        for (ExecuteUnit executeUnit : executeUnits) {
            //run executeUnit logic
            ExecuteUnit.ExecuteUnitResponse evalResult = executeUnit.exec();
            if (evalResult.isFired()) {
                fireExecuteUnits.add(executeUnit);
                List<Action> actions = evalResult.getActions() == null
                        ? new ArrayList<>() : evalResult.getActions();
                for (Action action : actions) {
                    if (action instanceof OutPut) {
                        //输出一个变量
                        OutPut outPut = (OutPut) action;
                        String key = outPut.getKey();
                        Object obj = outPut.getObj();
                        if (variables.containsKey(key)) {
                            //变量合并逻辑
                            OutPut.MergeStrategy mergeStrategy = outPut.getMergeStrategy();
                            Object mergeResult = mergeStrategy.merge(variables.get(key), obj);
                            variables.put(key, mergeResult);
                        } else {
                            variables.put(key, obj);
                        }
                    }
                }
            }
            execRules.add(executeUnit);
        }
        return response;
    }

    private DecisionChain getDecisionChain(Integer chainId) {
        Optional<DecisionChain> decisionUnitOp = resource.getDecisionChains().stream().filter(
                unit -> unit.getId().equals(chainId)).findAny();
        return decisionUnitOp.orElseThrow(() -> new IllegalArgumentException("不存在的决策单元 chainId = " + chainId));
    }
}

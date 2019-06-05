package com.aihulk.tech.engine;

import com.aihulk.tech.action.*;
import com.aihulk.tech.component.ScriptEngine;
import com.aihulk.tech.config.RuleEngineConfig;
import com.aihulk.tech.context.DecisionContext;
import com.aihulk.tech.exception.EngineInitException;
import com.aihulk.tech.exception.EngineNotInitException;
import com.aihulk.tech.resource.decision.DecisionRequest;
import com.aihulk.tech.resource.decision.DecisionResponse;
import com.aihulk.tech.resource.entity.*;
import com.aihulk.tech.resource.loader.ResourceLoader;
import com.aihulk.tech.util.JsonUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName DefaultEngine
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:44
 * @Version 1.0
 */
@Slf4j
public class DefaultEngine implements Engine {

    private Resource resource;

    private volatile boolean inited = false;

    private static ResourceLoader resourceLoader;

    private static ScriptEngine scriptEngine;

    static {
        try {
            resourceLoader = RuleEngineConfig.getResourceLoader().newInstance();
            scriptEngine = RuleEngineConfig.getScriptEngine().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EngineInitException("resourceLoader 初始化失败");
        }
    }

    @Override
    public void init(String version) throws EngineInitException {
        resource = resourceLoader.loadResource(version);
        inited = true;
    }

    @Override
    public DecisionResponse decision(DecisionRequest request) {
        DecisionResponse response = new DecisionResponse();

        //0.param check
        Preconditions.checkArgument(request.getUnitId() != null && request.getUnitId() > 0, "unitId 参数不合法");
        //1.check engine status
        if (!inited) throw new EngineNotInitException("engine 尚未初始化完成");
        //2.extract features
        DecisionChain decisionChain = getDecisionUnit(request.getUnitId());
        //3.iterate and eval rules
        Iterator<ExecuteUnitGroup> iterator = decisionChain.iterator();
        while (iterator.hasNext()) {
            ExecuteUnitGroup executeUnitGroup = iterator.next();
            this.evalRules(executeUnitGroup.getExecuteUnits(), response);
        }

        response.setStatus(0);
        response.setMsg("决策成功");
        return response;

    }

    private void evalRules(List<ExecuteUnit> executeUnits, DecisionResponse response) {
        List<ExecuteUnit> fireExecuteUnits = response.getFireExecuteUnits();
        List<ExecuteUnit> execRules = response.getExecExecuteUnits();
        Map<String, Object> variables = response.getVariables();
        for (int i = 0; i < executeUnits.size(); i++) {
            ExecuteUnit executeUnit = executeUnits.get(i);
            Map<Integer, Object> featureMap = extractFeature(executeUnit.getFacts());
            DecisionContext.setFeatureMap(featureMap);
            //3.run executeUnit logic
            if (executeUnit.eval()) {
                fireExecuteUnits.add(executeUnit);
                if (executeUnit.getAction() instanceof OutPut) {
                    //输出一个变量
                    OutPut outPut = (OutPut) executeUnit.getAction();
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
            execRules.add(executeUnit);
        }
    }


    private Map<Integer, Object> extractFeature(List<Fact> facts) {
        Map<String, Object> map = JsonUtil.parseObject(DecisionContext.getData(), Map.class);
        //构造抽取特征需要的对象
        List<ScriptEngine.ScriptInfo> scriptInfos = facts.stream().map(fact -> buildScriptInfo(fact, map)).collect(Collectors.toList());
        Map<Integer, Object> executeResults = scriptEngine.execute(scriptInfos);
        return executeResults;
    }

    private ScriptEngine.ScriptInfo buildScriptInfo(Fact fact, Map<String, Object> data) {
        ScriptEngine.ScriptInfo scriptInfo = new ScriptEngine.ScriptInfo(fact.getId(), fact.getCode(), data);
        return scriptInfo;
    }

    private DecisionChain getDecisionUnit(Integer unitId) {
        Optional<DecisionChain> decisionUnitOp = resource.getDecisionChains().stream().filter(
                unit -> unit.getId().equals(unitId)).findAny();
        return decisionUnitOp.orElseThrow(() -> new IllegalArgumentException("不存在的决策单元 unitId = " + unitId));
    }
}

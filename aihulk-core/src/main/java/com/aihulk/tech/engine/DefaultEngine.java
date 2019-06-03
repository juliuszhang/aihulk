package com.aihulk.tech.engine;

import com.aihulk.tech.action.JumpToRule;
import com.aihulk.tech.action.JumpToRuleSet;
import com.aihulk.tech.action.OutPut;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
        List<Rule> fireRules = response.getFireRules();
        List<Rule> execRules = response.getExecRules();
        Map<String, Object> variables = response.getVariables();
        //0.param check
        Preconditions.checkArgument(request.getUnitId() != null && request.getUnitId() > 0, "unitId 参数不合法");
        //1.check engine status
        if (!inited) throw new EngineNotInitException("engine 尚未初始化完成");
        //2.extract features
        DecisionUnit decisionUnit = getDecisionUnit(request.getUnitId());
        List<RuleSet> ruleSets = decisionUnit.getRuleSets();
        for (int j = 0; j < ruleSets.size(); j++) {
            RuleSet ruleSet = ruleSets.get(j);
            List<Rule> rules = ruleSet.getRules();
            for (int i = 0; i < rules.size(); i++) {
                Rule rule = rules.get(i);
                Map<Integer, Feature> featureMap = extractFeature(rule.getFeatures(), request.getData());
                DecisionContext.setFeatureMap(featureMap);
                //3.run rule logic
                if (rule.eval()) {
                    fireRules.add(rule);
                    //跳转规则逻辑
                    if (rule.getAction() instanceof JumpToRule) {
                        int index = findIndexOfRule(rules, ((JumpToRule) rule.getAction()).getRuleId());
                        if (index >= 0) {
                            i = index - 1;
                        }
                    }
                    //跳转到规则集逻辑
                    else if (rule.getAction() instanceof JumpToRuleSet) {
                        int index = findIndexOfRuleSet(ruleSets, ((JumpToRuleSet) rule.getAction()).getRuleSetId());
                        if (index >= 0) {
                            j = index - 1;
                            break;
                        }
                    } else if (rule.getAction() instanceof OutPut) {
                        //输出一个变量
                        OutPut outPut = (OutPut) rule.getAction();
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
                execRules.add(rule);
            }
        }

        response.setStatus(0);
        response.setMsg("决策成功");
        return response;

    }

    private int findIndexOfRuleSet(List<RuleSet> ruleSets, Integer ruleSetId) {
        for (int i = 0; i < ruleSets.size(); i++) {
            if (ruleSets.get(i).getId().equals(ruleSetId)) {
                return i;
            }
        }
        return -1;
    }

    private int findIndexOfRule(List<Rule> rules, Integer ruleId) {
        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).getId().equals(ruleId)) {
                return i;
            }
        }
        return -1;
    }

    private Map<Integer, Feature> extractFeature(List<Feature> features, Object data) {
        Map<String, Object> map = JsonUtil.parseObject((String) data, Map.class);
        Map<Integer, Feature> featureMap = features.stream().collect(Collectors.toMap(Feature::getId, Function.identity()));
        //构造抽取特征需要的对象
        List<ScriptEngine.ScriptInfo> scriptInfos = features.stream().map(feature -> buildScriptInfo(feature, map)).collect(Collectors.toList());
        Map<Integer, Object> executeResults = scriptEngine.execute(scriptInfos);
        for (Map.Entry<Integer, Object> entry : executeResults.entrySet()) {
            featureMap.get(entry.getKey()).setVal(entry.getValue());
        }
        return featureMap;
    }

    private ScriptEngine.ScriptInfo buildScriptInfo(Feature feature, Map<String, Object> data) {
        ScriptEngine.ScriptInfo scriptInfo = new ScriptEngine.ScriptInfo(feature.getId(), feature.getCode(), data);
        return scriptInfo;
    }

    private DecisionUnit getDecisionUnit(Integer unitId) {
        Optional<DecisionUnit> decisionUnitOp = resource.getDecisionUnits().stream().filter(
                unit -> unit.getId().equals(unitId)).findAny();
        return decisionUnitOp.orElseThrow(() -> new IllegalArgumentException("不存在的决策单元 unitId = " + unitId));
    }
}

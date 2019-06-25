package com.aihulk.tech.core.engine;

import com.aihulk.tech.core.action.OutPut;
import com.aihulk.tech.core.component.ScriptEngine;
import com.aihulk.tech.core.config.RuleEngineConfig;
import com.aihulk.tech.core.context.DecisionContext;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.exception.EngineNotInitException;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.resource.entity.*;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.core.util.JsonUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Preconditions.checkArgument(request.getChainId() != null && request.getChainId() > 0, "chainId 参数不合法");
        //1.check engine status
        if (!inited) throw new EngineNotInitException("engine 尚未初始化完成");
        //2.extract features
        DecisionChain decisionChain = getDecisionChain(request.getChainId());
        //3.iterate and eval rules
        Iterator<BasicUnit> iterator = decisionChain.iterator();
        while (iterator.hasNext()) {
            BasicUnit basicUnit = iterator.next();
            if (basicUnit.getType() == BasicUnit.UnitType.EXECUTE_UNIT) {
                ExecuteUnit executeUnit = (ExecuteUnit) basicUnit;
                this.evalRules(Arrays.asList(executeUnit), response);
            } else if (basicUnit.getType() == BasicUnit.UnitType.EXECUTE_UNIT_GROUP) {
                this.evalRules(((ExecuteUnitGroup) basicUnit).getExecuteUnits(), response);
            } else {
                throw new UnsupportedOperationException("unknown basic unit type");
            }
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
            extractFeature(executeUnit.getFacts());
//            DecisionContext.setFactMap(featureMap);
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


    private void extractFeature(List<Fact> facts) {
        Map<String, Object> map = JsonUtil.parseObject(DecisionContext.getData(), Map.class);
        //构造抽取特征需要的对象
        for (Fact fact : facts) {
            ScriptEngine.ScriptInfo scriptInfo = buildScriptInfo(fact, map);
            Object result = scriptEngine.execute(scriptInfo);
            DecisionContext.addFact(fact.getId(), result);
        }
//        List<ScriptEngine.ScriptInfo> scriptInfos = facts.stream().map(fact -> buildScriptInfo(fact, map)).collect(Collectors.toList());
//        Map<Integer, Object> executeResults = scriptEngine.execute(scriptInfos);
    }

    private static final String REF_FACT_REGEX = "\\$ref_fact_\\d{1,}";

    private ScriptEngine.ScriptInfo buildScriptInfo(Fact fact, Map<String, Object> data) {
        String code = fact.getCode();
        Pattern pattern = Pattern.compile(REF_FACT_REGEX);
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            String refFactSign = matcher.group();
            String factIdStr = refFactSign.replaceAll("\\$ref_fact_", "");
            int factId = Integer.parseInt(factIdStr);
            Object factVal = DecisionContext.getFactMap(factId);
            //处理code中事实引用部分的代码 将其替换为事实的真实值 由于特征前面有$需要加两个\\做转义
            code = code.replaceAll("\\" + refFactSign, factVal.toString());
        }
        ScriptEngine.ScriptInfo scriptInfo = new ScriptEngine.ScriptInfo(fact.getId(), code, data);
        return scriptInfo;
    }

    private DecisionChain getDecisionChain(Integer chainId) {
        Optional<DecisionChain> decisionUnitOp = resource.getDecisionChains().stream().filter(
                unit -> unit.getId().equals(chainId)).findAny();
        return decisionUnitOp.orElseThrow(() -> new IllegalArgumentException("不存在的决策单元 chainId = " + chainId));
    }
}

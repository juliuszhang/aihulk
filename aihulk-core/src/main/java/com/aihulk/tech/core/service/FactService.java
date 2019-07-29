package com.aihulk.tech.core.service;

import com.aihulk.tech.core.component.ScriptEngine;
import com.aihulk.tech.core.config.RuleEngineConfig;
import com.aihulk.tech.core.context.DecisionContext;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.resource.decision.DecisionData;
import com.aihulk.tech.core.resource.entity.Fact;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangyibo
 * @title: FactService
 * @projectName aihulk
 * @description: FactService
 * @date 2019-07-0214:01
 */
public class FactService {

    private static ScriptEngine scriptEngine;

    static {
        try {
            scriptEngine = RuleEngineConfig.getScriptEngine().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EngineInitException("scriptEngine 初始化失败");
        }
    }

    public void extractFeature(List<Fact> facts) {
        DecisionData data = DecisionContext.getData();
        //构造抽取特征需要的对象
        for (Fact fact : facts) {
            ScriptEngine.ScriptInfo scriptInfo = buildScriptInfo(fact, data);
            Object result = scriptEngine.execute(scriptInfo);
            DecisionContext.addFact(fact.getId(), result);
        }
    }

    private static final String REF_FACT_REGEX = "\\$ref_fact_\\d{1,}";

    private ScriptEngine.ScriptInfo buildScriptInfo(Fact fact, DecisionData data) {
        String code = fact.getCode();
        Pattern pattern = Pattern.compile(REF_FACT_REGEX);
        Matcher matcher = pattern.matcher(code);
        //TODO 从fact中获取到要查询的数据源 并获取数据源
        while (matcher.find()) {
            String refFactSign = matcher.group();
            String factIdStr = refFactSign.replaceAll("\\$ref_fact_", "");
            int factId = Integer.parseInt(factIdStr);
            Object factVal = DecisionContext.getFactMap(factId);
            //处理code中事实引用部分的代码 将其替换为事实的真实值 由于特征前面有$需要加两个\\做转义
            code = code.replaceAll("\\" + refFactSign, factVal.toString());
        }
        Map<String, Object> dataMap = Maps.newHashMapWithExpectedSize(1);
        dataMap.put("data", data.getData());
        ScriptEngine.ScriptInfo scriptInfo = new ScriptEngine.ScriptInfo(fact.getId(), code, dataMap);
        return scriptInfo;
    }

}

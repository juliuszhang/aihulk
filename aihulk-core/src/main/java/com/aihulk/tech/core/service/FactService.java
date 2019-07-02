package com.aihulk.tech.core.service;

import com.aihulk.tech.core.component.ScriptEngine;
import com.aihulk.tech.core.config.RuleEngineConfig;
import com.aihulk.tech.core.context.DecisionContext;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.resource.entity.Fact;
import com.aihulk.tech.core.util.JsonUtil;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangyibo
 * @title: FactService
 * @projectName aihulk
 * @description: TODO
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
        Map<String, Object> map = JsonUtil.parseObject(DecisionContext.getData(), Map.class);
        //构造抽取特征需要的对象
        for (Fact fact : facts) {
            ScriptEngine.ScriptInfo scriptInfo = buildScriptInfo(fact, map);
            Object result = scriptEngine.execute(scriptInfo);
            DecisionContext.addFact(fact.getId(), result);
        }
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

}

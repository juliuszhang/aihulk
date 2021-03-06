package com.aihulk.tech.core.service;

import com.aihulk.tech.core.component.JsScriptEngine;
import com.aihulk.tech.core.config.RuleEngineConfig;
import com.aihulk.tech.core.config.RuleEngineConfigHolder;
import com.aihulk.tech.core.engine.DefaultEngine;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.resource.loader.LocalTestResourceLoader;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 16:17
 * @Description:
 */
public class DecisionServiceTest {

    @Test
    public void decision() {
        RuleEngineConfigHolder.setInitializer(() ->
                RuleEngineConfig.builder().engine(DefaultEngine.class)
                        .resourceLoader(LocalTestResourceLoader.class)
                        .scriptEngine(JsScriptEngine.class).build());
        DecisionService decisionService = new DecisionService();
        Map<String, Object> applyData = new HashMap<>();
        Map<String, Object> applyMap = Maps.newHashMapWithExpectedSize(1);
        applyMap.put("age", 30);
        applyData.put("apply", applyMap);
        DecisionRequest request = new DecisionRequest(applyData, 1);
        DecisionResponse response = decisionService.decision(request, 1, "test");
        System.out.println(response);
    }
}

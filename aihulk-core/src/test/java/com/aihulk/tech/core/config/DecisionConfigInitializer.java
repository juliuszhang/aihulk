package com.aihulk.tech.core.config;

import com.aihulk.tech.core.component.JsScriptEngine;
import com.aihulk.tech.core.engine.DefaultEngine;
import com.aihulk.tech.core.resource.loader.LocalTestResourceLoader;

/**
 * @author zhangyibo
 * @title: DecisionConfigInitializer
 * @projectName aihulk
 * @description: TODO
 * @date 2019-12-05 11:35
 */
public class DecisionConfigInitializer implements RuleEngineConfigInitializer {
    @Override
    public RuleEngineConfig init() {
        return RuleEngineConfig.builder().engine(DefaultEngine.class)
                .resourceLoader(LocalTestResourceLoader.class)
                .scriptEngine(JsScriptEngine.class).build();
    }
}

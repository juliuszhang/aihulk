package com.aihulk.tech.core.config;


import com.aihulk.tech.core.component.JsScriptEngine;
import com.aihulk.tech.core.component.ScriptEngine;
import com.aihulk.tech.core.engine.DefaultEngine;
import com.aihulk.tech.core.engine.Engine;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.resource.loader.ResourceLoader;

/**
 * @ClassName RuleEngineConfig
 * @Description RuleEngineConfig
 * @Author yibozhang
 * @Date 2019/5/1 12:48
 * @Version 1.0
 */
public class RuleEngineConfig {

    private Class<? extends Engine> engineClass = DefaultEngine.class;

    private Class<? extends ResourceLoader<?>> resourceLoader = null;

    private Class<? extends ScriptEngine> scriptEngine = JsScriptEngine.class;

    public Class<? extends Engine> getEngineClass() {
        if (engineClass == null)
            throw new EngineInitException("engineClass不能为空");
        return engineClass;
    }

    public Class<? extends ResourceLoader<?>> getResourceLoader() {
        if (resourceLoader == null)
            throw new EngineInitException("resourceLoader不能为空");
        return resourceLoader;
    }

    public Class<? extends ScriptEngine> getScriptEngine() {
        if (scriptEngine == null)
            throw new EngineInitException("scriptEngine不能为空");
        return scriptEngine;
    }

    public static RuleEngineConfigBuilder builder() {
        return new RuleEngineConfig().new RuleEngineConfigBuilder();
    }

    class RuleEngineConfigBuilder {

        public RuleEngineConfigBuilder engine(Class<? extends Engine> engineClass) {
            RuleEngineConfig.this.engineClass = engineClass;
            return this;
        }

        public RuleEngineConfigBuilder scriptEngine(Class<? extends ScriptEngine> scriptEngineClass) {
            RuleEngineConfig.this.scriptEngine = scriptEngineClass;
            return this;
        }

        public RuleEngineConfigBuilder resourceLoader(Class<? extends ResourceLoader<?>> resourceLoaderClass) {
            RuleEngineConfig.this.resourceLoader = resourceLoaderClass;
            return this;
        }

        public RuleEngineConfig build() {
            return RuleEngineConfig.this;
        }
    }

}

package com.aihulk.tech.config;


import com.aihulk.tech.component.ScriptEngine;
import com.aihulk.tech.engine.Engine;
import com.aihulk.tech.exception.EngineInitException;
import com.aihulk.tech.resource.loader.ResourceLoader;
import org.yaml.snakeyaml.Yaml;

/**
 * @ClassName RuleEngineConfig
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:48
 * @Version 1.0
 */
public class RuleEngineConfig {

    private static final String RULE_ENGINE_YAML_PATH = "/rule_engine.yml";

    private static final RuleEngineConfigEntity CONFIG = new Yaml().loadAs(RuleEngineConfig.class.getResourceAsStream(RULE_ENGINE_YAML_PATH), RuleEngineConfigEntity.class);

    public static Class<Engine> getEngineClass() {
        String engineType = CONFIG.getEngineType();
        try {
            return (Class<Engine>) Class.forName(engineType);
        } catch (ClassNotFoundException e) {
            throw new EngineInitException("找不到指定的engine ,engine type=" + engineType);
        }
    }

    public static Class<ResourceLoader> getResourceLoader() {
        String resourceLoaderClass = CONFIG.getResourceLoader();
        try {
            return (Class<ResourceLoader>) Class.forName(resourceLoaderClass);
        } catch (ClassNotFoundException e) {
            throw new EngineInitException("找不到指定的resourceLoader ,resourceLoader = " + resourceLoaderClass);
        }
    }

    public static Class<ScriptEngine> getScriptEngine() {
        String scriptEngineClass = CONFIG.getScriptEngine();
        try {
            return (Class<ScriptEngine>) Class.forName(scriptEngineClass);
        } catch (ClassNotFoundException e) {
            throw new EngineInitException("找不到指定的scriptEngine ,scriptEngine = " + scriptEngineClass);
        }
    }

    public static String getResourceFilePath() {
        return CONFIG.getResourceFilePath();
    }


}

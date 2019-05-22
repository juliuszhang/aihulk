package com.aihulk.tech.engine;

import com.aihulk.tech.config.RuleEngineConfig;
import com.aihulk.tech.exception.EngineInitException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @ClassName EngineStore
 * @Author yibozhang
 * @Date 2019/5/1 12:32
 * @Version 1.0
 */
@Slf4j
public class EngineStore {

    private static final Map<String, Engine> ENGINE_STORE = Maps.newConcurrentMap();

    /**
     * 获取engine
     *
     * @param version
     * @return
     */
    public static Engine getEngine(String version) {
        //double check
        if (!ENGINE_STORE.containsKey(version)) {
            synchronized (EngineStore.class) {
                if (!ENGINE_STORE.containsKey(version)) {
                    //construct instance
                    Engine engine = constructEngine(version);
                    ENGINE_STORE.put(version, engine);
                }
            }
        }
        return ENGINE_STORE.get(version);
    }

    private static Engine constructEngine(String version) {
        Class<Engine> engineClass = RuleEngineConfig.getEngineClass();
        Engine engine;
        try {
            engine = engineClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("初始化engine 失败,engine type = {}", engineClass);
            throw new EngineInitException("初始化engine 失败,engine type = " + engineClass);
        }
        engine.init(version);
        return engine;
    }

}

package com.aihulk.tech.core.engine;

import com.aihulk.tech.core.config.RuleEngineConfig;
import com.aihulk.tech.core.exception.EngineInitException;
import com.google.common.collect.Maps;
import lombok.Data;
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

    @Data
    public static class EngineKey {
        private Integer bizId;

        private String version;

        public EngineKey(Integer bizId, String version) {
            this.bizId = bizId;
            this.version = version;
        }
    }

    private static final Map<EngineKey, Engine> ENGINE_STORE = Maps.newConcurrentMap();

    /**
     * 获取engine
     *
     * @param version
     * @return
     */
    public static Engine getEngine(Integer bizId, String version) {
        //double check
        EngineKey engineKey = new EngineKey(bizId, version);
        if (!ENGINE_STORE.containsKey(engineKey)) {
            synchronized (EngineStore.class) {
                if (!ENGINE_STORE.containsKey(version)) {
                    //construct instance
                    Engine engine = constructEngine(bizId, version);
                    ENGINE_STORE.put(engineKey, engine);
                }
            }
        }
        return ENGINE_STORE.get(version);
    }

    private static Engine constructEngine(Integer bizId, String version) {
        Class<Engine> engineClass = RuleEngineConfig.getEngineClass();
        Engine engine;
        try {
            engine = engineClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("初始化engine 失败,engine type = {}", engineClass);
            throw new EngineInitException("初始化engine 失败,engine type = " + engineClass);
        }
        engine.init(bizId, version);
        return engine;
    }

}

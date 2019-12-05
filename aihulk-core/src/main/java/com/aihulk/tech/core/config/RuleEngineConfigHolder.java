package com.aihulk.tech.core.config;

import com.aihulk.tech.core.exception.EngineInitException;
import lombok.Setter;

/**
 * @author zhangyibo
 * @title: RuleEngineConfigHolder
 * @projectName aihulk
 * @description: 目前对于所有的biz和version来说都使用一份配置
 * @date 2019-12-05 11:21
 */
public class RuleEngineConfigHolder {

    private volatile static RuleEngineConfig config;

    @Setter
    private static RuleEngineConfigInitializer initializer = null;

    public static RuleEngineConfig config() {
        if (config == null)
            if (initializer == null) throw new EngineInitException("initializer 不能为空");
        synchronized (RuleEngineConfigHolder.class) {
            if (config == null) {
                config = initializer.init();
            }
        }
        return config;
    }
}

package com.aihulk.tech.core.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName RuleEngineConfigEntity
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:50
 * @Version 1.0
 */
@Getter
@Setter
public class RuleEngineConfigEntity {

    private String engineType;

    private String resourceLoader;

    private String resourceFilePath;

    private String scriptEngine;
}

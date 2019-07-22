package com.aihulk.tech.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangyibo
 * @title: ScriptCodeType
 * @projectName aihulk
 * @description: script脚本代码类型
 * @date 2019-07-0411:50
 */
@AllArgsConstructor
public enum ScriptCodeType {
    JS("js"),
    PY("py"),
    BASIC("basic");

    @Getter
    private String name;

}

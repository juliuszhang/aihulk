package com.aihulk.tech.core.constant;

import lombok.Getter;

/**
 * @ClassName ScriptEngineType
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/2/22 21:45
 * @Version 1.0
 */
public enum ScriptEngineType {

    JS_ENGINE("js"),
    PY_ENGINE("py");

    @Getter
    private String language;

    ScriptEngineType(String language) {
        this.language = language;
    }
}

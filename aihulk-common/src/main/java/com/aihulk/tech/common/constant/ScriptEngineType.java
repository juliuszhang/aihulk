package com.aihulk.tech.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ScriptEngineType
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/2/22 21:45
 * @Version 1.0
 */
@AllArgsConstructor
public enum ScriptEngineType {

    JS_ENGINE(ScriptCodeType.JS),
    PY_ENGINE(ScriptCodeType.PY);

    @Getter
    private ScriptCodeType scriptCodeType;
}

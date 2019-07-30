package com.aihulk.tech.manage.service;

import com.aihulk.tech.common.constant.ScriptCodeType;
import com.aihulk.tech.core.component.BasicScriptEngine;
import com.aihulk.tech.core.component.JsScriptEngine;
import com.aihulk.tech.core.component.ScriptEngine;
import com.aihulk.tech.entity.entity.Fact;
import com.aihulk.tech.entity.mapper.FactMapper;
import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.vo.base.BaseResponseVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhangyibo
 * @title: FactService
 * @projectName aihulk
 * @description: FactService
 * @date 2019-06-2616:53
 */
@Slf4j
@Service
public class FactService extends BaseService<Fact, FactMapper> {

    private static final String FUNCTION_NAME_PLACEHOLDER = "#{fName}";
    private static final String CODE_BODY_PLACEHOLDER = "#{codeBody}";
    private static final String CODE_FORMAL_PARAMS_PLACEHOLDER = "#{formalParams}";
    private static final String CODE_PARAMS_PLACEHOLDER = "#{params}";

    private static final String JS_CODE_TEMPLATE =
            "function " + FUNCTION_NAME_PLACEHOLDER + "(" + CODE_FORMAL_PARAMS_PLACEHOLDER + "){" +
                    "return " + CODE_BODY_PLACEHOLDER + ";" +
                    "}\n" +
                    //function invoke
                    FUNCTION_NAME_PLACEHOLDER + "(" + CODE_PARAMS_PLACEHOLDER + ");";

    private static final String FUNCTION_NAME_PREFIX = "func";


    public String formatScript(ScriptCodeType type, Integer scriptId, String script) {
        String result;
        String formalParamsString = "data";
        //替换方法名
        result = JS_CODE_TEMPLATE.replace(FUNCTION_NAME_PLACEHOLDER, FUNCTION_NAME_PREFIX + scriptId)
                //替换形参
                .replace(CODE_FORMAL_PARAMS_PLACEHOLDER, formalParamsString)
                //替换方法调用处
                .replace(FUNCTION_NAME_PLACEHOLDER, FUNCTION_NAME_PREFIX + scriptId)
                .replace(CODE_PARAMS_PLACEHOLDER, formalParamsString);
        if (type == ScriptCodeType.JS) {
            //替换方法体
            result = result.replace(CODE_BODY_PLACEHOLDER, "data." + script);
        } else if (type == ScriptCodeType.BASIC) {
            //替换方法体
            result = result.replace(CODE_BODY_PLACEHOLDER, "data.apply." + script);
        } else {
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.UNKNOWN_SCRIPT_ENGING, "未知的script code type.code type = " + type.getName());
        }
        return result;
    }

    public Object exec(ScriptCodeType type, Integer scriptId, String script, Map<String, Object> params, boolean isFormat) {
        ScriptEngine scriptEngine;
        Map<String, Object> execParams = Maps.newHashMapWithExpectedSize(1);
        if (ScriptCodeType.JS == (type)) {
            scriptEngine = new BasicScriptEngine();
            execParams.put("data", params);
        } else if (ScriptCodeType.BASIC == type) {
            Map<String, Object> apply = Maps.newHashMapWithExpectedSize(1);
            apply.put("apply", params);
            execParams.put("data", apply);
            scriptEngine = new JsScriptEngine();
        } else if (ScriptCodeType.PY == type) {
            throw new UnsupportedOperationException();
        } else {
            log.error("can not find script engine for this code type. code type name = {}", type.getName());
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.UNKNOWN_SCRIPT_ENGING, "找不到合适的脚本引擎，code type=" + type.getName());
        }
        String exeScript;
        if (!isFormat) {
            exeScript = this.formatScript(type, scriptId, script);
        } else {
            exeScript = script;
        }
        return scriptEngine.execute(new ScriptEngine.ScriptInfo(scriptId, exeScript, execParams));
    }

}

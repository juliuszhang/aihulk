package com.aihulk.tech.core.component;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: SimpleScriptEngine
 * @projectName aihulk
 * @description: SimpleScriptEngine 执行简单的基础事实脚本代码
 * @date 2019-07-29 10:47
 */
public class SimpleScriptEngine implements ScriptEngine {

    private static final String FUNCTION_NAME_PLACEHOLDER = "${fName}";
    private static final String CODE_BODY_PLACEHOLDER = "#{codeBody}";

    private static final String JS_CODE_TEMPLATE =
            "function" + FUNCTION_NAME_PLACEHOLDER + "(data){" +
                    "return data." + CODE_BODY_PLACEHOLDER + ";" +
                    "}\n" +
                    //function invoke
                    FUNCTION_NAME_PLACEHOLDER + "(data);";

    private static final ScriptEngine SCRIPT_ENGINE = new JsScriptEngine();

    /**
     * 脚本是类似data.apply.a.b这样的形式
     * 简单处理包装成js代码去执行
     *
     * @param scriptInfo
     * @return
     */
    @Override
    public Object execute(ScriptInfo scriptInfo) {
        String jsScript = JS_CODE_TEMPLATE.replace(FUNCTION_NAME_PLACEHOLDER, " fun" + scriptInfo.getScriptId())
                .replace(CODE_BODY_PLACEHOLDER, scriptInfo.getScript())
                .replace(FUNCTION_NAME_PLACEHOLDER, " fun" + scriptInfo.getScriptId());

        ScriptInfo jsScriptInfo = new ScriptInfo(scriptInfo.getScriptId(), jsScript, scriptInfo.getParams());
        return SCRIPT_ENGINE.execute(jsScriptInfo);
    }

    @Override
    public Map<Integer, Object> execute(List<ScriptInfo> scriptInfos) {
        Map<Integer, Object> resultMap = Maps.newHashMapWithExpectedSize(scriptInfos.size());
        for (ScriptInfo scriptInfo : scriptInfos) {
            Object result = this.execute(scriptInfo);
            resultMap.put(scriptInfo.getScriptId(), result);
        }
        return resultMap;
    }


}

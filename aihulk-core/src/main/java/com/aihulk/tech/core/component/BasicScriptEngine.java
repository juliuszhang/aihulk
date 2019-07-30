package com.aihulk.tech.core.component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: SimpleScriptEngine
 * @projectName aihulk
 * @description: SimpleScriptEngine 执行简单的基础事实脚本代码
 * @date 2019-07-29 10:47
 */
public class BasicScriptEngine implements ScriptEngine {

    private static final ScriptEngine SCRIPT_ENGINE = new JsScriptEngine();

    @Override
    public Object execute(ScriptInfo scriptInfo) {
        return SCRIPT_ENGINE.execute(scriptInfo);
    }

    @Override
    public Map<Integer, Object> execute(List<ScriptInfo> scriptInfos) {
        return SCRIPT_ENGINE.execute(scriptInfos);
    }


}

package com.aihulk.tech.component;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsScriptEngineTest {

    @Test
    public void execute() {

        JsScriptEngine scriptEngine = new JsScriptEngine();
        Map<String, Object> params = new HashMap<>();
        Map<String,Object> obj = new HashMap<>();
        obj.put("name","zhangsan");
        obj.put("age",20);
        params.put("data", obj);
        ScriptEngine.ScriptInfo scriptInfo = new ScriptEngine.ScriptInfo(1, "function $feature_001(data){ return data.age} \n $feature_001(data);",params);
        Object execute = scriptEngine.execute(scriptInfo);
        System.out.println(execute);
    }
}
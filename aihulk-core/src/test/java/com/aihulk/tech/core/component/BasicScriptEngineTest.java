package com.aihulk.tech.core.component;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author zhangyibo
 * @title: SimpleScriptEngineTest
 * @projectName aihulk
 * @description: SimpleScriptEngineTest
 * @date 2019-07-29 11:10
 */
public class BasicScriptEngineTest {

    @Test
    public void execute() {
        String script = "data.apply.a";
        Map<String, Object> paramMap = Maps.newHashMap();
        Map<String, Object> applyMap = Maps.newHashMap();
        Map<String, Object> aMap = Maps.newHashMap();
        aMap.put("a", "test success");
        applyMap.put("apply", aMap);
        paramMap.put("data", applyMap);

        BasicScriptEngine basicScriptEngine = new BasicScriptEngine();
        Object execute = basicScriptEngine.execute(new ScriptEngine.ScriptInfo(1, script, paramMap));
        Assert.assertTrue(execute.equals("test success"));
    }
}

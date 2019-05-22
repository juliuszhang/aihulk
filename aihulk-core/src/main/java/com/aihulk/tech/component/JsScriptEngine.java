package com.aihulk.tech.component;

import com.aihulk.tech.exception.ScriptExecuteException;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;
import javax.script.Bindings;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @Auther: zhangyibo
 * @Date: 2019/1/22 14:50
 * @Description: JS脚本执行引擎
 * warn:如果要在JDK8中执行ES6脚本 需要配置如下jvm参数开启nashorn执行ES6的功能
 * -Dnashorn.args=--language=es6
 */
@ThreadSafe
@Slf4j
public class JsScriptEngine implements ScriptEngine {

    //使用jdk8的nashorn引擎执行js脚本
    private static final String JS_ENGINE_SHORT_NAME = "nashorn";

    private final ThreadLocal<ScriptEngineManager> SCRIPT_ENGINE_MANAGERS = new ThreadLocal<>();

    @Override
    public Object execute(ScriptInfo scriptInfo) {
        return executeOne(scriptInfo).get(scriptInfo.getScriptId());
    }

    @Override
    public Map<Integer, Object> execute(List<ScriptInfo> scriptInfos) {
        //暂时先用jdk的fork join自动分配线程 后面发现性能不行再调
        List<Map<Integer, Object>> mapList = scriptInfos.parallelStream()
                .map(this::executeOne).collect(Collectors.toList());
        Map<Integer,Object> result = Maps.newHashMapWithExpectedSize(mapList.size());
        mapList.forEach(result::putAll);
        return result;
    }

    private Map<Integer,Object> executeOne(ScriptInfo scriptInfo) {
        try {
            //1.param check
            paramsCheck(scriptInfo);
            //2.bind args
            javax.script.ScriptEngine scriptEngine = getScriptEngineManager().getEngineByName(JS_ENGINE_SHORT_NAME);
            Bindings bindings = scriptEngine.createBindings();
            bindings.putAll(scriptInfo.getParams());
            //4.eval script
            Object scriptResult = scriptEngine.eval(scriptInfo.getScript(), bindings);
            Map<Integer,Object> result = Maps.newHashMapWithExpectedSize(1);
            result.put(scriptInfo.getScriptId(),scriptResult);
            return result;
        } catch (ScriptException e) {
            log.error("execute script exception,scriptId = " + scriptInfo.getScriptId(), e);
            throw new ScriptExecuteException("execute script exception,scriptId = "+ scriptInfo.getScriptId());
        }
    }

    private void paramsCheck(ScriptInfo scriptInfo) {
        checkNotNull(scriptInfo, "scriptInfo can not be null");
        checkNotNull(scriptInfo.getScriptId(), "scriptId can not be null");
        checkArgument(!Strings.isNullOrEmpty(scriptInfo.getScript()), "script can not be null");
    }

    private ScriptEngineManager getScriptEngineManager() {
        if (SCRIPT_ENGINE_MANAGERS.get() == null) {
            SCRIPT_ENGINE_MANAGERS.set(new ScriptEngineManager());
        }

        return SCRIPT_ENGINE_MANAGERS.get();
    }

}


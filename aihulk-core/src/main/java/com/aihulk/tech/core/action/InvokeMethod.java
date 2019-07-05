package com.aihulk.tech.core.action;

import java.lang.reflect.Method;

/**
 * @author zhangyibo
 * @title: InvokeMethod
 * @projectName aihulk
 * @description: 调用本地方法
 * @date 2019-07-0515:59
 */
public class InvokeMethod implements Action {

    private Class<?> clazz;

    private Method method;

    private Object[] args;

    public Object execute() throws Exception {
        return this.method.invoke(clazz.newInstance(), args);
    }

}

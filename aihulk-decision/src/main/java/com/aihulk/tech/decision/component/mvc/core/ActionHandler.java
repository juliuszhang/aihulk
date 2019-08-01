package com.aihulk.tech.decision.component.mvc.core;

import java.lang.reflect.Method;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:ActionHandler
 * @Description: 将请求处理类和处理方法封装成一个对象以方便后续调用
 * @date 2019/8/1
 */
class ActionHandler {
    
    private final Class<?> router;
    
    private final Method method;
    
    ActionHandler(Class<?> router, Method method) {
        this.router = router;
        this.method = method;
    }
    
    Class<?> getRouter() {
        return router;
    }
    
    Method getMethod() {
        return method;
    }
}

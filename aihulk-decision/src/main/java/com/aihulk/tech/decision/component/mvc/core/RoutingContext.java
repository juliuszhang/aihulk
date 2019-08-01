package com.aihulk.tech.decision.component.mvc.core;

import com.aihulk.tech.decision.component.mvc.annotation.RequestMethod;
import java.util.Arrays;
import java.util.Map;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:RoutingContext
 * @Description:
 * @date 2019/8/1
 */
class RoutingContext extends AbstractContext {
    private static class InstanceHolder {
        private static final RoutingContext INSTANCE = new RoutingContext();
    }
    
    private RoutingContext() {
        super();
    }
    
    static RoutingContext getRoutingContext() {
        return InstanceHolder.INSTANCE;
    }
    
    ActionHandler getActionHandler(String path, RequestMethod... requestMethods) {
        for (Map.Entry<RoutingRequest, ActionHandler> entry : actionMap.entrySet()) {
            RequestMethod[] allowedMethods = entry.getKey().getRequestMethods();
            if (entry.getKey().getPath().equals(path)) {
                if (Arrays.asList(allowedMethods).containsAll(Arrays.asList(requestMethods))) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}

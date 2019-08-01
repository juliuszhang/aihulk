package com.aihulk.tech.decision.component.mvc.core;

import com.aihulk.tech.decision.component.mvc.annotation.Action;
import com.aihulk.tech.decision.component.mvc.annotation.RequestMethod;
import com.aihulk.tech.decision.component.mvc.annotation.Router;
import com.aihulk.tech.decision.config.DecisionConfig;
import com.aihulk.tech.decision.config.DecisionConfigEntity;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:AbstractContext
 * @Description:
 * @date 2019/8/1
 */
@Slf4j
class AbstractContext {

    protected static final DecisionConfigEntity.ServerConfig CONFIG = DecisionConfig.getServerConfig();

    private volatile boolean initialized;

    private final String basePackage;

    // holds all controller.
    private final Set<Class<?>> routers = Sets.newHashSet();
    // holds all injected singletons.
    private final static Map<Class<?>, Object> SINGLETONS = Maps.newHashMap();

    final Map<RoutingRequest, ActionHandler> actionMap = new ConcurrentHashMap<>();

    AbstractContext() {
        this.basePackage = CONFIG.getBasePackage();
        init();
    }

    void init() {
        if (!initialized) {
            Set<Class<?>> classes = ClassTracker.loadClasses(basePackage);
            synchronized (this) {
                for (Class<?> clazz : classes) {
                    if (clazz.isAnnotationPresent(Router.class)) {
                        routers.add(clazz);
                    }
                    SINGLETONS.put(clazz, ClassTracker.newInstance(clazz));
                }
                if (!routers.isEmpty()) {
                    buildActionMap();
                }
                initialized = true;
            }
        }
    }

    private void buildActionMap() {
        for (Class<?> router : routers) {
            Method[] methods = router.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Action.class)) {
                    Action action = method.getAnnotation(Action.class);
                    String path = action.value();
                    RequestMethod[] requestMethods = action.method();
                    RoutingRequest routingRequest = new RoutingRequest(path, requestMethods);
                    ActionHandler handler = new ActionHandler(router, method);
                    log.info(String.format("Mapped url \"%s\" for \"%s-%s()\"", path, router.getName(),
                            method.getName()));
                    this.actionMap.put(routingRequest, handler);
                }
            }
        }
    }

    boolean isInitialized() {
        return initialized;
    }

    Map<Class<?>, Object> getSingletons() {
        return SINGLETONS;
    }

}

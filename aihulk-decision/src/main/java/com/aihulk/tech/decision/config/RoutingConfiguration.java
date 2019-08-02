package com.aihulk.tech.decision.config;

import com.aihulk.tech.decision.handler.DecisionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author zhangyibo
 * @title: RoutingConfiguration
 * @projectName aihulk
 * @description: RoutingConfiguration
 * @date 2019-08-02 11:30
 */
@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(DecisionHandler decisionHandler) {
        return route(POST("/decision/{chainId}").and(accept(APPLICATION_JSON)), decisionHandler::decision);
    }

}

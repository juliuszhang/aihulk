package com.aihulk.tech.decision.handler;

import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.service.DecisionService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:DecisionHandler
 * @Description:DecisionHandler
 * @date 2019/8/1
 */
@Component
public class DecisionHandler {

    private DecisionService decisionService;

    public Mono<ServerResponse> decision(ServerRequest request) {
        Integer chainId = Integer.parseInt(request.pathVariable("chainId"));
        DecisionResponse response = new DecisionResponse();
        return ServerResponse.ok().body(Mono.just(response), DecisionResponse.class);
    }

}

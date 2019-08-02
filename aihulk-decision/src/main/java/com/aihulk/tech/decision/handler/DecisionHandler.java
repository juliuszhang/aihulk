package com.aihulk.tech.decision.handler;

import com.aihulk.tech.core.resource.decision.DecisionResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:DecisionController
 * @Description: DecisionController
 * @date 2019/8/1
 */
@Component
public class DecisionHandler {

    public Mono<ServerResponse> decision(ServerRequest request) {
        DecisionResponse response = new DecisionResponse();
        return ServerResponse.ok().body(Mono.just(response), DecisionResponse.class);
    }

}

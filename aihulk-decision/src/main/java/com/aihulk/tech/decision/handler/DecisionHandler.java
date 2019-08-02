package com.aihulk.tech.decision.handler;

import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.service.DecisionService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:DecisionHandler
 * @Description:DecisionHandler
 * @date 2019/8/1
 */
@Component
public class DecisionHandler {

    private DecisionService decisionService = new DecisionService();

    public Mono<ServerResponse> decision(ServerRequest request) {
        Integer chainId = Integer.parseInt(request.pathVariable("chainId"));
        Integer bizId = Integer.parseInt(request.pathVariable("bizId"));
        DecisionRequest decisionRequest = new DecisionRequest();
        decisionRequest.setChainId(chainId);
        Mono<Map<String, Object>> mapMono = request.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> apply = mapMono.block();
        decisionRequest.setData(apply);
        decisionService.decision(decisionRequest, bizId, null);
        DecisionResponse response = new DecisionResponse();
        return ServerResponse.ok().body(Mono.just(response), DecisionResponse.class);
    }

}

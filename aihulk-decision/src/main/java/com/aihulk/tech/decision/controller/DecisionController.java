package com.aihulk.tech.decision.controller;

import com.aihulk.tech.core.exception.RuleEngineException;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.service.DecisionService;
import com.google.common.base.Preconditions;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:DecisionController
 * @Description:DecisionController
 * @date 2019/8/1
 */
@RestController
@RequestMapping(value = "/decision")
public class DecisionController {

    private DecisionService decisionService = new DecisionService();

    @PostMapping(value = "/{bizId}/{chainId}")
    public Mono<DecisionResponse> decision(@PathVariable(value = "bizId") Integer bizId,
                                           @PathVariable(value = "chainId") Integer chainId,
                                           @RequestBody Map<String, Object> apply) {
        checkArgument(bizId > 0, "bizId参数不合法");
        checkArgument(chainId > 0, "chainid参数不合法");
        return Mono.just(decisionService.decision(new DecisionRequest(apply, chainId), bizId, null))
                .onErrorResume(RuleEngineException.class, (e) -> Mono.just(new DecisionResponse(e.getCode().getCode(), e.getMessage())));
    }

}

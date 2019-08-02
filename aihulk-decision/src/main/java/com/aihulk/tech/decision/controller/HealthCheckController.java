package com.aihulk.tech.decision.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:HeathCheckController
 * @Description: HeathCheckController
 * @date 2019/8/2
 */
@RestController
@RequestMapping(value = "/health")
public class HealthCheckController {

    @GetMapping(value = "")
    public Mono<Void> helthCheck() {
        return Mono.empty();
    }

}

package com.aihulk.tech.core.service;

import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import org.junit.Test;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 16:17
 * @Description:
 */
public class DecisionServiceTest {

    @Test
    public void decision() {
        DecisionService decisionService = new DecisionService();
        DecisionRequest request = new DecisionRequest();
        request.setData("{\"data\":{\"age\":30}}");
        request.setChainId(1);
        DecisionResponse response = decisionService.decision(request, "test");
        System.out.println(response);
    }
}

package com.aihulk.tech.service;

import com.aihulk.tech.resource.decision.DecisionRequest;
import com.aihulk.tech.resource.decision.DecisionResponse;
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
        request.setData("{\"data\":{\"name\":\"zhangsan\",\"age\":26}}");
        request.setUnitId(1);
        DecisionResponse response = decisionService.decision(request, "test");
        System.out.println(response);
    }
}
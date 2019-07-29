package com.aihulk.tech.core.service;

import com.aihulk.tech.core.resource.decision.DecisionData;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

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
        DecisionData applyData = new DecisionData();
        Map<String, Object> applyMap = Maps.newHashMapWithExpectedSize(1);
        applyMap.put("age", 30);
        applyData.addData(DecisionData.APPLY_DATA_KEY, applyMap);
        request.setData(applyData);
        request.setChainId(1);
        DecisionResponse response = decisionService.decision(request, 1, "test");
        System.out.println(response);
    }
}

package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.util.JsonUtil;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;
import com.aihulk.tech.core.service.DecisionService;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * @author zhangyibo
 * @title: DecisionServiceTest
 * @projectName aihulk
 * @description: DecisionServiceTest
 * @date 2019-08-09 15:40
 */
public class DecisionServiceTest extends AbstractTest {

    @Test
    public void decision() {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        Map<String, Object> applyMap = Maps.newHashMapWithExpectedSize(1);
        applyMap.put("age", 30);
        paramMap.put("apply", applyMap);
        DecisionRequest decisionRequest = new DecisionRequest(paramMap, chain.getId());
        DecisionResponse response = new DecisionService().decision(decisionRequest, bizId, "");
        System.out.println(JsonUtil.toJsonString(response));
    }

}

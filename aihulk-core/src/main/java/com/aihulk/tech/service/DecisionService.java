package com.aihulk.tech.service;


import com.aihulk.tech.context.DecisionContext;
import com.aihulk.tech.engine.Engine;
import com.aihulk.tech.engine.EngineStore;
import com.aihulk.tech.resource.decision.DecisionRequest;
import com.aihulk.tech.resource.decision.DecisionResponse;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 16:15
 * @Description:
 */
public class DecisionService {

    public DecisionResponse decision(DecisionRequest decisionRequest, String version) {
        Engine engine = EngineStore.getEngine(version);
        DecisionContext.setData(decisionRequest.getData());
        DecisionResponse response = engine.decision(decisionRequest);
        return response;
    }

}

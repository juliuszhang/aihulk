package com.aihulk.tech.core.service;


import com.aihulk.tech.core.context.DecisionContext;
import com.aihulk.tech.core.engine.Engine;
import com.aihulk.tech.core.engine.EngineStore;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 16:15
 * @Description:
 */
public class DecisionService {

    public DecisionResponse decision(DecisionRequest decisionRequest, Integer bizId, String version) {
        Engine engine = EngineStore.getEngine(bizId, version);
        DecisionContext.setData(decisionRequest.getData());
        return engine.decision(decisionRequest);
    }

}

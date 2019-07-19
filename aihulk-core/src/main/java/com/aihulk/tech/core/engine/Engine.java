package com.aihulk.tech.core.engine;


import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.resource.decision.DecisionRequest;
import com.aihulk.tech.core.resource.decision.DecisionResponse;

/**
 * @ClassName Engine
 * @Description Engine
 * @Author yibozhang
 * @Date 2019/5/1 12:32
 * @Version 1.0
 */
public interface Engine {


    void init(Integer bizId, String version) throws EngineInitException;

    /**
     * 决策
     *
     * @param request
     * @return
     */
    DecisionResponse decision(DecisionRequest request);

}

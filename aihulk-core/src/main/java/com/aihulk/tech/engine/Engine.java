package com.aihulk.tech.engine;


import com.aihulk.tech.exception.EngineInitException;
import com.aihulk.tech.resource.decision.DecisionRequest;
import com.aihulk.tech.resource.decision.DecisionResponse;

/**
 * @ClassName Engine
 * @Description TODO
 * @Author yibozhang
 * @Date 2019/5/1 12:32
 * @Version 1.0
 */
public interface Engine {


    void init(String version) throws EngineInitException;

    /**
     * 决策
     *
     * @param request
     * @return
     */
    DecisionResponse decision(DecisionRequest request);

}

package com.aihulk.tech.core.resource.decision;

import lombok.Data;

@Data
public class DecisionRequest {
    /**
     * 决策数据
     */
    private DecisionData data;

    /**
     * 决策链id
     */
    private Integer chainId;

}

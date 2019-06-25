package com.aihulk.tech.core.resource.decision;

import lombok.Data;

@Data
public class DecisionRequest {
    /**
     * 决策数据
     */
    private String data;

    /**
     * 决策链id
     */
    private Integer chainId;
}

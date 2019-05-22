package com.aihulk.tech.resource.decision;

import lombok.Data;

@Data
public class DecisionRequest {
    /**
     * 决策数据
     */
    private String data;

    /**
     * 决策单元ID
     */
    private Integer unitId;
}
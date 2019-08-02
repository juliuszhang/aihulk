package com.aihulk.tech.core.resource.decision;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class DecisionRequest {
    /**
     * 决策数据 {apply:xx}
     */
    private Map<String, Object> data;

    /**
     * 决策链id
     */
    private Integer chainId;

}

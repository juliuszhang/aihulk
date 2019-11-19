package com.aihulk.tech.core.resource.decision;

import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class DecisionResponse {
    private Integer status;

    private String msg;

    private List<ExecuteUnit> fireExecuteUnits = Lists.newArrayList();

    private List<ExecuteUnit> execExecuteUnits = Lists.newArrayList();

    private Map<String, Object> variables = Maps.newHashMap();

    public DecisionResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}

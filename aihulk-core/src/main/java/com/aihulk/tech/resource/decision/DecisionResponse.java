package com.aihulk.tech.resource.decision;

import com.aihulk.tech.resource.entity.Rule;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DecisionResponse {
    private Integer status;

    private String msg;

    private List<Rule> fireRules = Lists.newArrayList();

    private List<Rule> execRules = Lists.newArrayList();

    private Map<String, Object> variables = Maps.newHashMap();
}
package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.logic.Logic;
import com.aihulk.tech.core.service.FactService;
import lombok.Data;

import java.util.List;

/**
 * @author zhangyibo
 * @title: DecisionFlow
 * @projectName aihulk
 * @description: 普通决策流
 * @date 2019-07-0211:46
 */
@Data
public class DecisionFlow extends ExecuteUnit {

    private Logic logic;

    private List<Action> actions;

    @Override
    public ExecuteUnitResponse eval() {
        FactService factService = new FactService();
        factService.extractFeature(this.facts);
        boolean fired = logic.eval();
        ExecuteUnitResponse response = new ExecuteUnitResponse();
        response.setFired(fired);
        response.setActions(this.actions);
        return response;
    }

}
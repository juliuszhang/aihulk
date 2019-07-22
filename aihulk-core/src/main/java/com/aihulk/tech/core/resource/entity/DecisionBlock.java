package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.logic.Express;
import com.aihulk.tech.core.service.FactService;
import lombok.Data;

import java.util.List;

/**
 * @author zhangyibo
 * @title: DecisionBlock
 * @projectName aihulk
 * @description: 普通决策流
 * @date 2019-07-0211:46
 */
@Data
public class DecisionBlock extends ExecuteUnit<ExecuteUnit.ExecuteUnitResponse> {

    private Express express;

    private List<Action> actions;

    @Override
    public ExecuteUnitResponse exec() {
        FactService factService = new FactService();
        factService.extractFeature(this.facts);
        ExecuteUnitResponse response = new ExecuteUnitResponse();
        response.setFired(express.eval());
        response.setActions(this.actions);
        return response;
    }

}

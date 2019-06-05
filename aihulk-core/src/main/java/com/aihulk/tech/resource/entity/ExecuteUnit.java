package com.aihulk.tech.resource.entity;

import com.aihulk.tech.action.Action;
import com.aihulk.tech.logic.EvalAble;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ExecuteUnit
 * @Description 执行单元
 * @Author yibozhang
 * @Date 2019/5/1 12:20
 * @Version 1.0
 */
@Data
public class ExecuteUnit extends BaseResource implements EvalAble, BasicUnit {

    private Express express;

    private Action action;

    private List<Fact> facts = Lists.newArrayList();

    @Override
    public boolean eval() {
        return express.eval();
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    @Override
    public UnitType getType() {
        return UnitType.EXECUTE_UNIT;
    }
}

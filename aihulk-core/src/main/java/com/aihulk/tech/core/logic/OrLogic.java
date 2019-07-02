package com.aihulk.tech.core.logic;

import java.util.List;

/**
 * @author zhangyibo
 * @title: OrLogic
 * @projectName aihulk
 * @description: TODO
 * @date 2019-07-0118:46
 */
public class OrLogic implements Logic {

    private List<Logic> logics;

    public OrLogic(List<Logic> logics) {
        this.logics = logics;
    }

    @Override
    public Boolean eval() {
        for (Logic logic : logics) {
            if (logic.eval()) return true;
        }
        return false;
    }
}

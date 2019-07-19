package com.aihulk.tech.core.logic;

import java.util.List;

/**
 * @author zhangyibo
 * @title: OrLogic
 * @projectName aihulk
 * @description: OrLogic
 * @date 2019-07-0118:46
 */
public class OrLogic implements Logic {

    private List<Logic> logics;

    public OrLogic(List<Logic> logics) {
        this.logics = logics;
    }

    @Override
    public boolean eval() {
        for (Logic logic : logics) {
            if (logic.eval()) return true;
        }
        return false;
    }
}

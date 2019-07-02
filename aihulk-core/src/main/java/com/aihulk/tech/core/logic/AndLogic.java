package com.aihulk.tech.core.logic;

import java.util.List;

/**
 * @author zhangyibo
 * @title: AndLogic
 * @projectName aihulk
 * @description: TODO
 * @date 2019-07-0118:43
 */
public class AndLogic implements Logic {

    private List<Logic> logics;

    public AndLogic(List<Logic> logics) {
        this.logics = logics;
    }

    @Override
    public boolean eval() {
        for (Logic logic : logics) {
            if (!logic.eval()) return false;
        }
        return true;
    }
}

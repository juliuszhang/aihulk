package com.aihulk.tech.core.logic;

import java.util.List;

/**
 * @author zhangyibo
 * @title: AndExpress
 * @projectName aihulk
 * @description: AndExpress
 * @date 2019-07-0118:43
 */
public class AndExpress implements Express {

    private List<Express> expresses;

    public AndExpress(List<Express> expresses) {
        this.expresses = expresses;
    }

    @Override
    public boolean eval() {
        for (Express express : expresses) {
            if (!express.eval()) return false;
        }
        return true;
    }
}

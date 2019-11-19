package com.aihulk.tech.core.logic;

import java.util.List;

/**
 * @author zhangyibo
 * @title: OrExpress
 * @projectName aihulk
 * @description: OrExpress
 * @date 2019-07-0118:46
 */
public class OrExpress implements Express {

    private List<Express> expresses;

    public OrExpress(List<Express> expresses) {
        this.expresses = expresses;
    }

    @Override
    public boolean eval() {
        return expresses.stream().anyMatch(Express::eval);
    }
}

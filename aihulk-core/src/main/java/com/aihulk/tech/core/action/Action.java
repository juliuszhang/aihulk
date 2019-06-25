package com.aihulk.tech.core.action;

public interface Action {

    default String getActionName() {
        return this.getClass().getSimpleName();
    }

}

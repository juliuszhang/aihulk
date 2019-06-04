package com.aihulk.tech.action;

public interface Action {

    default String getActionName() {
        return this.getClass().getSimpleName();
    }

}

package com.aihulk.tech.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangyibo
 * @title: ActionType
 * @projectName aihulk
 * @description: ActionType
 * @date 2019-07-0511:36
 */
@AllArgsConstructor
public enum ActionType {

    OUTPUT(1),
    CONSOLE_OUTPUT(2);

    @Getter
    private Integer val;

}

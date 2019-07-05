package com.aihulk.tech.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangyibo
 * @title: ExecuteUnitType
 * @projectName aihulk
 * @description: 执行单元类型
 * @date 2019-07-0511:25
 */
@AllArgsConstructor
public enum ExecuteUnitType {

    DECISION_FLOW(1),
    DECISION_TABLE(2),
    DECISION_TREE(3);

    @Getter
    private Integer val;
}

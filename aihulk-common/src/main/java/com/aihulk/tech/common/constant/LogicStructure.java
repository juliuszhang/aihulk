package com.aihulk.tech.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangyibo
 * @title: LogicStructure
 * @projectName aihulk
 * @description: 逻辑表达式结构
 * @date 2019-07-0511:10
 */
@AllArgsConstructor
public enum LogicStructure {
    //普通决策流
    DECISION_FLOW(1),
    //决策表
    DECISION_TABLE(2),
    //决策树
    DECISION_TREE(3),
    //流程规则
    FLOW_RULE(4);

    @Getter
    private Integer val;

}

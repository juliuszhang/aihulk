package com.aihulk.tech.core.logic;

import com.aihulk.tech.core.action.Action;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author zhangyibo
 * @title: DecisionTable
 * @projectName aihulk
 * @description: 决策表
 * @date 2019-07-0117:32
 */
@Data
public class DecisionTable implements EvalAble {

    private List<Cell> cells = Lists.newArrayList();

    @Override
    public boolean eval() {
        for (Cell cell : cells) {
        }
        return true;
    }

    @Data
    public static class Cell implements EvalAble {
        private int row;

        private int col;

        private int rowSpan;

        private Express express;

        private Action action;

        @Override
        public boolean eval() {
            return express.eval();
        }
    }
}

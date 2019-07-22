package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.logic.Express;
import com.aihulk.tech.core.service.FactService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: DecisionTable
 * @projectName aihulk
 * @description: 决策表
 * @date 2019-07-0117:32
 */
@Data
public class DecisionTable extends ExecuteUnit<ExecuteUnit.ExecuteUnitResponse> {

    private Map<String, Cell> cellMap;

    private List<Row> rows;

    private List<Col> cols;

    @Override
    public ExecuteUnit.ExecuteUnitResponse exec() {
        //extract features
        FactService factService = new FactService();
        factService.extractFeature(this.facts);

        ExecuteUnitResponse response = new ExecuteUnitResponse();
        List<Action> firedActions = Lists.newArrayList();
        for (Row row : rows) {
            boolean rowHit = true;
            for (Col col : cols) {
                Cell cell = getCell(row.getNum(), col.getNum());
                if (Col.TYPE_CONDITION == col.getType()) {
                    if (!cell.express.eval()) {
                        rowHit = false;
                        break;
                    }
                } else if (Col.TYPE_RESULT == col.getType()) {
                    //如果前面的条件单元列都命中了
                    if (rowHit) {
                        firedActions.add(cell.getValue());
                    }
                }
            }
            //只要有一个命中 就设置为触发
            if (rowHit) {
                response.setFired(true);
            }
        }
        response.setActions(firedActions);
        return response;
    }

    public Cell getCell(int row, int col) {
        for (int i = row; row >= 0; i--) {
            Cell cell = cellMap.get(i + "," + col);
            if (cell != null) return cell;
        }
        return null;
    }


    @Data
    public static class Cell {
        private int row;

        private int col;

        private int rowSpan;

        private Express express;

        private Action value;
    }

    @Data
    @AllArgsConstructor
    public static class Row {

        private int num;

        private int height;
    }

    @Data
    @AllArgsConstructor
    public static class Col {

        //条件列
        public static final Integer TYPE_CONDITION = 0;

        //结果列
        public static final Integer TYPE_RESULT = 1;

        private int num;

        private int width;

        private String labelName;

        private Integer type;
    }


}

package com.aihulk.tech.core.action;

import com.aihulk.tech.common.constant.MergeStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName OutPut
 * @Description 变量输出动作
 * @Author yibozhang
 * @Date 2019/6/5 11:51
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class OutPut implements Action {

    private String key;

    private Object obj;

    private MergeStrategy unitMergeStrategy;

    private MergeStrategy chainMergeStrategy;

    //高位表示决策链合并逻辑
    public static final Integer MERGE_STRATEGY_CHAIN_MASK = 1 << 1;
    //低位表示执行单元合并逻辑
    public static final Integer MERGE_STRATEGY_UNIT_MASK = 1 << 0;


    public OutPut(String key, Object obj, Integer allMergeStrategy) {
        this.key = key;
        this.obj = obj;
        Integer unitStrategy = allMergeStrategy & MERGE_STRATEGY_UNIT_MASK;
        Integer chainStrategy = allMergeStrategy & MERGE_STRATEGY_CHAIN_MASK;
        for (MergeStrategy value : MergeStrategy.values()) {
            if (value.getVal() == unitStrategy) {
                this.unitMergeStrategy = value;
            }
            if (value.getVal() == (chainStrategy >> 1)) {
                this.chainMergeStrategy = value;
            }
        }
    }

}

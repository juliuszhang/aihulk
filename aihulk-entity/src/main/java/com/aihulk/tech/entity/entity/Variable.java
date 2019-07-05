package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: Variable
 * @projectName aihulk
 * @description: 变量
 * @date 2019-06-2814:53
 */
@Data
@TableName(value = "variable")
@EqualsAndHashCode(callSuper = true)
public class Variable extends BaseEntity {

    //执行单元合并逻辑 取第一个结果返回
    public static final Integer MERGE_STRATEGY_UNIT_FIRST = 1;
    //执行单元合并逻辑 全部返回
    public static final Integer MERGE_STRATEGY_UNIT_ALL = 2;
    //执行单元合并逻辑 取最后一个结果返回
    public static final Integer MERGE_STRATEGY_UNIT_LAST = 3;

    //决策链合并逻辑 取第一个结果返回
    public static final Integer MERGE_STRATEGY_CHAIN_FIRST = 1 << 1;
    //决策链合并逻辑 全部返回
    public static final Integer MERGE_STRATEGY_CHAIN_ALL = 2 << 1;
    //决策链合并逻辑 取最后一个结果返回
    public static final Integer MERGE_STRATEGY_CHAIN_LAST = 3 << 1;

    //高位表示决策链合并逻辑
    public static final Integer MERGE_STRATEGY_CHAIN_MASK = 1 << 1;
    //低位表示执行单元合并逻辑
    public static final Integer MERGE_STRATEGY_UNIT_MASK = 1 << 0;

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "business_id")
    private Integer businessId;

    @TableField(value = "type")
    private String type;

    @TableField(value = "merge_strategy")
    private Integer mergeStrategy;

    public Integer getUnitStrategy() {
        return this.mergeStrategy & MERGE_STRATEGY_UNIT_MASK;
    }

    public Integer getChainStrategy() {
        return this.mergeStrategy & MERGE_STRATEGY_CHAIN_MASK;
    }

}

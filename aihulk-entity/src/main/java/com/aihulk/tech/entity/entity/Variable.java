package com.aihulk.tech.entity.entity;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.MergeStrategy;
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
    public static final Integer MERGE_STRATEGY_UNIT_FIRST = MergeStrategy.NOTOVERWRITE.getVal();
    //执行单元合并逻辑 全部返回
    public static final Integer MERGE_STRATEGY_UNIT_ALL = MergeStrategy.ALL.getVal();
    //执行单元合并逻辑 取最后一个结果返回
    public static final Integer MERGE_STRATEGY_UNIT_LAST = MergeStrategy.OVERWRITE.getVal();

    //决策链合并逻辑 取第一个结果返回
    public static final Integer MERGE_STRATEGY_CHAIN_FIRST = MergeStrategy.NOTOVERWRITE.getVal() << 1;
    //决策链合并逻辑 全部返回
    public static final Integer MERGE_STRATEGY_CHAIN_ALL = MergeStrategy.ALL.getVal() << 1;
    //决策链合并逻辑 取最后一个结果返回
    public static final Integer MERGE_STRATEGY_CHAIN_LAST = MergeStrategy.OVERWRITE.getVal() << 1;

    //高位表示决策链合并逻辑
    public static final Integer MERGE_STRATEGY_CHAIN_MASK = 1 << 1;
    //低位表示执行单元合并逻辑
    public static final Integer MERGE_STRATEGY_UNIT_MASK = 1 << 0;

    public static final String TYPE_NUMBER = DataType.NUMBER.getName();

    public static final String TYPE_STRING = DataType.STRING.getName();

    public static final String TYPE_OBJECT = DataType.OBJECT.getName();

    public static final String TYPE_ARRAY_NUMBER = DataType.ARRAY_NUMBER.getName();

    public static final String TYPE_ARRAY_STRING = DataType.ARRAY_STRING.getName();

    public static final String TYPE_ARRAY_OBJECT = DataType.ARRAY_OBJECT.getName();

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

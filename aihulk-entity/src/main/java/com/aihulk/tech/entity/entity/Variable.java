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
    public static final Integer MERGE_STRATEGY_CHAIN_FIRST = MergeStrategy.NOTOVERWRITE.getVal() << 16;
    //决策链合并逻辑 全部返回
    public static final Integer MERGE_STRATEGY_CHAIN_ALL = MergeStrategy.ALL.getVal() << 16;
    //决策链合并逻辑 取最后一个结果返回
    public static final Integer MERGE_STRATEGY_CHAIN_LAST = MergeStrategy.OVERWRITE.getVal() << 16;

    //低16位表示执行单元合并逻辑
    public static final Integer UNIT_MERGE_STRATEGY_MASK = Integer.MAX_VALUE >>> 16;
    //高16位表示决策链合并逻辑
    public static final Integer CHAIN_MERGE_STRATEGY_MASK = Integer.MAX_VALUE << 16;

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

    public MergeStrategy getUnitStrategy() {
        return Variable.getUnitStrategy(this.mergeStrategy);
    }

    public MergeStrategy getChainStrategy() {
        return Variable.getChainStrategy(this.mergeStrategy);
    }

    public static MergeStrategy getUnitStrategy(Integer mergeStrategy) {
        return MergeStrategy.parse(mergeStrategy & UNIT_MERGE_STRATEGY_MASK);
    }

    public static MergeStrategy getChainStrategy(Integer mergeStrategy) {
        return MergeStrategy.parse(mergeStrategy >>> 16);
    }

}

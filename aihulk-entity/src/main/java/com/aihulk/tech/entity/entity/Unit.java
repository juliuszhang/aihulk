package com.aihulk.tech.entity.entity;

import com.aihulk.tech.common.constant.ExecuteUnitType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: Unit
 * @date 2019-06-0314:11
 */
@TableName(value = "unit")
@Data
@EqualsAndHashCode(callSuper = true)
public class Unit extends BaseEntity {

    public static final Integer TYPE_DECISION_BLOCK = ExecuteUnitType.DECISION_BLOCK.getVal();

    public static final Integer TYPE_DECISION_TABLE = ExecuteUnitType.DECISION_TABLE.getVal();

    public static final Integer TYPE_DECISION_TREE = ExecuteUnitType.DECISION_TREE.getVal();

    @TableField(value = "business_id")
    private Integer businessId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "`type`")
    private Integer Type;

}

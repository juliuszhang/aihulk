package com.aihulk.tech.entity.entity;

import com.aihulk.tech.common.constant.LogicStructure;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: Logic
 * @projectName aihulk
 * @description: Logic
 * @date 2019-07-0511:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "logic")
public class Logic extends BaseEntity {

    public static final Integer STRUCTURE_DECISION_FLOW = LogicStructure.DECISION_FLOW.getVal();
    public static final Integer STRUCTURE_DECISION_TABLE = LogicStructure.DECISION_TABLE.getVal();
    public static final Integer STRUCTURE_DECISION_TREE = LogicStructure.DECISION_TREE.getVal();
    public static final Integer STRUCTURE_FLOW_RULE = LogicStructure.FLOW_RULE.getVal();

    @TableField(value = "business_id")
    private Integer businessId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "relation_id")
    private Integer relationId;

    @TableField(value = "structure")
    private Integer structure;

    @TableField(value = "logic_exp")
    private String logicExp;

}

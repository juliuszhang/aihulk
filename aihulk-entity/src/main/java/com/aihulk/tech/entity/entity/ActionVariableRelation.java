package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: ActionVariableRelation
 * @projectName aihulk
 * @description: 动作和变量表关系
 * @date 2019-08-08 15:21
 */
@Data
@TableName(value = "action_variable_relation")
@EqualsAndHashCode(callSuper = true)
public class ActionVariableRelation extends BaseEntity {

    @TableField(value = "action_id")
    private Integer actionId;

    @TableField(value = "variable_id")
    private Integer variableId;

    @TableField(value = "value")
    private String value;

}

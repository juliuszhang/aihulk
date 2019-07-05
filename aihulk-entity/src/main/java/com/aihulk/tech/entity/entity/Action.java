package com.aihulk.tech.entity.entity;

import com.aihulk.tech.common.constant.ActionType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: Action
 * @projectName aihulk
 * @description: 执行单元执行后的动作
 * @date 2019-07-0511:34
 */
@Data
@TableName(value = "action")
@EqualsAndHashCode(callSuper = true)
public class Action extends BaseEntity {

    public static final Integer ACTION_TYPE_OUTPUT = ActionType.OUTPUT.getVal();

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "business_id")
    private Integer businessId;

    @TableField(value = "type")
    private Integer type;

}

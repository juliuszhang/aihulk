package com.aihulk.tech.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
@TableName(value = "unit")
@Data
@EqualsAndHashCode(callSuper = true)
public class Unit extends BaseEntity {

    public static final Integer TYPE_DECISION_FLOW = 0;

    public static final Integer TYPE_DECISION_TABLE = 1;

    @TableField(value = "biz_id")
    private Integer bizId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "action")
    private String action;

    @TableField(value = "eval_str")
    private String evalStr;

    @TableField(value = "`type`")
    private Integer Type;

}

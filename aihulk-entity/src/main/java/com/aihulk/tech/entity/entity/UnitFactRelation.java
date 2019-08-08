package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: UnitFactRelation
 * @projectName aihulk
 * @description: UnitFactRelation
 * @date 2019-08-08 15:17
 */
@Data
@TableName(value = "unit_fact_relation")
@EqualsAndHashCode(callSuper = true)
public class UnitFactRelation extends BaseEntity {

    @TableField(value = "unit_id")
    private Integer unitId;

    @TableField(value = "fact_id")
    private Integer factId;

}

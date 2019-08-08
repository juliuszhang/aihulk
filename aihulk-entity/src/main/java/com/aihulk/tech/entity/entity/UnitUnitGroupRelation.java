package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: UnitUnitGroupRelation
 * @projectName aihulk
 * @description: 执行单元和执行单元组关联关系
 * @date 2019-08-08 15:19
 */
@Data
@TableName(value = "unit_unit_group_relation")
@EqualsAndHashCode(callSuper = true)
public class UnitUnitGroupRelation extends BaseEntity {

    @TableField(value = "unit_id")
    private Integer unitId;

    @TableField(value = "unit_group_id")
    private Integer unitGroupId;

}

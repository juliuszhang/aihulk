package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: UnitGroup
 * @date 2019-06-0314:11
 */
@TableName(value = "unit_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class UnitGroup extends BaseEntity {

    @TableField(value = "biz_id")
    private Integer bizId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

}

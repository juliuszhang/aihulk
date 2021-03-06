package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: Chain
 * @date 2019-06-0314:11
 */
@TableName(value = "chain")
@Data
@EqualsAndHashCode(callSuper = true)
public class Chain extends BaseEntity {

    @TableField(value = "business_id")
    private Integer businessId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;


}

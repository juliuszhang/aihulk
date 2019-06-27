package com.aihulk.tech.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@Data
@TableName(value = "business")
@EqualsAndHashCode(callSuper = true)
public class Business extends BaseEntity {

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

}

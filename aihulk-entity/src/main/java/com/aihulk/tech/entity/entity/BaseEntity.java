package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: BaseEntity
 * @date 2019-06-0314:11
 */
@Data
public abstract class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    @TableField(value = "operator", fill = FieldFill.INSERT_UPDATE)
    private String operator;

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "deleted")
    private Integer deleted;

}

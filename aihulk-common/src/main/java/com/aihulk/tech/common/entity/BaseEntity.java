package com.aihulk.tech.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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

    @TableId
    private Integer id;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;

    @TableField(value = "operator")
    private String operator;

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "deleted")
    private Integer deleted;

}

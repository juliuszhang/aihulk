package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: FactRelation
 * @projectName aihulk
 * @description: 事实引用关系
 * @date 2019-08-08 15:18
 */
@Data
@TableName(value = "fact_relation")
@EqualsAndHashCode(callSuper = true)
public class FactRelation extends BaseEntity {

    @TableField(value = "fact_id")
    private Integer factId;

    @TableField(value = "ref_fact_id")
    private Integer refFactId;

}

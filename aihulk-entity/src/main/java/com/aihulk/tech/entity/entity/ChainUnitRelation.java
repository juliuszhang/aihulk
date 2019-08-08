package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: ChainUnitRelation
 * @projectName aihulk
 * @description: 决策链和执行单元关联关系
 * @date 2019-08-08 15:20
 */
@Data
@TableName(value = "chain_unit_relation")
@EqualsAndHashCode(callSuper = true)
public class ChainUnitRelation extends BaseEntity {

    @TableField(value = "chain_id")
    private Integer chainId;

    @TableField(value = "unit_id")
    private Integer unitId;

}

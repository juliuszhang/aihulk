package com.aihulk.tech.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyibo
 * @title: FlowRule
 * @projectName aihulk
 * @description: 流程规则
 * @date 2019-07-0115:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_rule")
public class FlowRule extends BaseEntity {

    @TableField(value = "biz_id")
    private Integer bizId;

    @TableField(value = "chain_id")
    private Integer chainId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "src_type")
    private Integer srcType;

    @TableField(value = "dest_type")
    private Integer destType;

    @TableField(value = "express")
    private String express;

    @TableField(value = "src_id")
    private Integer srcId;

    @TableField(value = "dest_id")
    private Integer destId;
}

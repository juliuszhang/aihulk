package com.aihulk.tech.entity.entity;

import com.aihulk.tech.common.constant.UnitType;
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

    public static final Integer UNIT_TYPE_EXECUTE_UNIT = UnitType.EXECUTE_UNIT.getVal();
    public static final Integer UNIT_TYPE_EXECUTE_GROUP = UnitType.EXECUTE_UNIT_GROUP.getVal();

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

    @TableField(value = "src_id")
    private Integer srcId;

    @TableField(value = "dest_id")
    private Integer destId;
}

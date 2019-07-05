package com.aihulk.tech.core.resource.entity;


import com.aihulk.tech.common.constant.UnitType;

/**
 * @author zhangyibo
 * @title: BasicUnit
 * @projectName aihulk
 * @description: 单元(目前分为执行单元和执行单元组) 都可直接用于决策链中
 * @date 2019-06-0516:04
 */
public interface BasicUnit {

    UnitType getUnitType();

}

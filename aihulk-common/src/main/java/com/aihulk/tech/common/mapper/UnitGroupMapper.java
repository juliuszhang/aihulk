package com.aihulk.tech.common.mapper;

import com.aihulk.tech.common.entity.UnitGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
public interface UnitGroupMapper extends BaseMapper<UnitGroup> {

    @Select(value = "SELECT eug.* FROM execute_unit_group as eug,decision_chain_execute_unit_group dceug WHERE " +
            "dceug.group_id = eug.id " +
            "AND dceug.chain_id = #{decisionUnitId}")
    List<UnitGroup> findByDecisionUnitId(@Param(value = "chainId") Integer chainId);

}

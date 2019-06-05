package com.aihulk.tech.mapper;

import com.aihulk.tech.entity.ExecuteUnitGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhangyibo
 * @title: ExecuteUnitGroupMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
public interface ExecuteUnitGroupMapper extends Mapper<ExecuteUnitGroupMapper> {

    @Select(value = "SELECT eug.* FROM execute_unit_group as eug,decision_chain_execute_unit_group dceug WHERE " +
            "dceug.group_id = eug.id " +
            "AND dceug.chain_id = #{decisionUnitId}")
    List<ExecuteUnitGroup> findByDecisionUnitId(@Param(value = "chainId") Integer chainId);

}

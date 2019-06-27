package com.aihulk.tech.common.mapper;

import com.aihulk.tech.common.entity.Unit;
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
public interface UnitMapper extends BaseMapper<Unit> {

    @Select(value = "SELECT execute_unit.* FROM execute_unit,execute_unit_execute_unit_group WHERE execute_unit.id = execute_unit_execute_unit_group.execute_unit_id AND execute_unit_execute_unit_group.execute_unit_group_id = #{ruleSetId}")
    List<Unit> selectByExecuteUnitGroupId(@Param(value = "executeUnitGroupId") Integer executeUnitGroupId);

}

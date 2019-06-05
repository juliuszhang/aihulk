package com.aihulk.tech.mapper;

import com.aihulk.tech.entity.Unit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
public interface UnitMapper extends Mapper<Unit> {

    @Select(value = "SELECT execute_unit.* FROM execute_unit,execute_unit_execute_unit_group WHERE execute_unit.id = execute_unit_execute_unit_group.execute_unit_id AND execute_unit_execute_unit_group.execute_unit_group_id = #{ruleSetId}")
    List<Unit> selectByExecuteUnitGroupId(@Param(value = "executeUnitGroupId") Integer executeUnitGroupId);

}

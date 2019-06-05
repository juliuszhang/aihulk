package com.aihulk.tech.mapper;

import com.aihulk.tech.entity.ExecuteUnit;
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
public interface ExecuteUnitMapper extends Mapper<ExecuteUnit> {

    @Select(value = "SELECT execute_unit.* FROM execute_unit,execute_unit_execute_unit_group WHERE execute_unit.id = execute_unit_execute_unit_group.execute_unit_id AND execute_unit_execute_unit_group.execute_unit_group_id = #{ruleSetId}")
    List<ExecuteUnit> selectByExecuteUnitGroupId(@Param(value = "executeUnitGroupId") Integer executeUnitGroupId);

}

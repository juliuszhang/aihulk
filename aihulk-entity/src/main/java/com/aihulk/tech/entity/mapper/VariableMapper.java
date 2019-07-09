package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.Variable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: VariableMapper
 * @projectName aihulk
 * @description: VariableMapper
 * @date 2019-06-2815:43
 */
public interface VariableMapper extends BaseMapper<Variable> {

    @Results({@Result(column = "name", property = "name"),
            @Result(column = "name_en", property = "nameEn"),
            @Result(column = "business_id", property = "businessId"),
            @Result(column = "type", property = "type"),
            @Result(column = "merge_strategy", property = "mergeStrategy"),
            @Result(column = "value", property = "value"),
            @Result(column = "action_id", property = "actionId")})
    @Select(value = "SELECT v.*,avr.value,avr.action_id FROM action_variable_relation avr,variable v" +
            " WHERE avr.variable_id = v.id")
    List<Map<String, Object>> selectAll();

}

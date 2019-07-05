package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.Variable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    @Select(value = "SELECT v.*,avr.value FROM action_variable_relation avr,variable v" +
            " WHERE avr.variable_id = v.id")
    List<Map<String, Object>> selectAll();

}

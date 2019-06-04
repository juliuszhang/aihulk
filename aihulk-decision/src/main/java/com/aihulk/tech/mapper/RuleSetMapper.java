package com.aihulk.tech.mapper;

import com.aihulk.tech.entity.RuleSet;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhangyibo
 * @title: RuleSetMapper
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0314:11
 */
public interface RuleSetMapper extends Mapper<RuleSet> {

    @Select(value = "SELECT rule_set.* FROM rule_set,decision_unit_rule_set WHERE " +
            "decision_unit_rule_set.rule_set_id = rule_set.id " +
            "AND decision_unit_rule_set.decision_unit_id = #{decisionUnitId}")
    List<RuleSet> findByDecisionUnitId(@Param(value = "decisionUnitId") Integer decisionUnitId);

}

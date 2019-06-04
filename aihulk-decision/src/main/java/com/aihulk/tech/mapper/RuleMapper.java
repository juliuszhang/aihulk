package com.aihulk.tech.mapper;

import com.aihulk.tech.entity.Rule;
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
public interface RuleMapper extends Mapper<Rule> {

    @Select(value = "SELECT rule.* FROM rule,rule_set_rule WHERE rule.id = rule_set_rule.rule_id AND rule_set_rule.rule_set_id = #{ruleSetId}")
    List<Rule> selectByRuleSetId(@Param(value = "ruleSetId") Integer ruleSetId);

}

package com.aihulk.tech.common.mapper;

import com.aihulk.tech.common.entity.Fact;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhangyibo
 * @title: FactMapper
 * @projectName aihulk
 * @description: feature mapper
 * @date 2019-06-0314:22
 */
@org.apache.ibatis.annotations.Mapper
public interface FactMapper extends Mapper<Fact> {

    @Select(value = "SELECT feature.* FROM feature,rule_feature WHERE feature.id = rule_feature.feature_id AND rule_feature.rule_id = #{ruleId}")
    List<Fact> selectByRuleId(@Param(value = "ruleId") Integer ruleId);

}
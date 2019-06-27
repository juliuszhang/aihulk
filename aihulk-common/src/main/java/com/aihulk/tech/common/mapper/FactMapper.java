package com.aihulk.tech.common.mapper;

import com.aihulk.tech.common.entity.Fact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangyibo
 * @title: FactMapper
 * @projectName aihulk
 * @description: feature mapper
 * @date 2019-06-0314:22
 */
public interface FactMapper extends BaseMapper<Fact> {

    @Select(value = "SELECT feature.* FROM feature,rule_feature WHERE feature.id = rule_feature.feature_id AND rule_feature.rule_id = #{ruleId}")
    List<Fact> selectByRuleId(@Param(value = "ruleId") Integer ruleId);

}

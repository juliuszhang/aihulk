package com.aihulk.tech.mapper;

import com.aihulk.tech.entity.Feature;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhangyibo
 * @title: FeatureMapper
 * @projectName aihulk
 * @description: feature mapper
 * @date 2019-06-0314:22
 */
public interface FeatureMapper extends Mapper<Feature> {

    @Select(value = "SELECT feature.* FROM feature,rule_feature WHERE feature.id = rule_feature.feature_id AND rule_feature.rule_id = #{ruleId}")
    List<Feature> selectByRuleId(@Param(value = "ruleId") Integer ruleId);

}

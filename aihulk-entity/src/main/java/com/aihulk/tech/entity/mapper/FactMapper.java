package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.Fact;
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

    @Select(value = "SELECT feature.* FROM fact,unit_fact_relation WHERE fact.id = unit_fact_relation.fact_id AND unit_fact_relation.unit_id = #{unitId}")
    List<Fact> selectByUnitId(@Param(value = "unitId") Integer unitId);

    @Select(value = "SELECT feature.id FROM fact,fact_relation WHERE fact.id = fact_relation.ref_fact_id AND fact_relation.fact_id = #{factId}")
    List<Integer> selectRefFacts(Integer factId);

}

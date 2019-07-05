package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.Chain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: 决策链mapper
 * @date 2019-06-0314:11
 */
public interface ChainMapper extends BaseMapper<Chain> {

    @Select(value = "SELECT * FROM chain_unit_relation WHERE `type` = #{type}")
    List<Map<String, Object>> selectUnitChainRelationsByType(Integer type);

}

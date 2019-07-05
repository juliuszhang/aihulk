package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.Unit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: UnitMapper
 * @date 2019-06-0314:11
 */
public interface UnitMapper extends BaseMapper<Unit> {

    @Select(value = "SELECT unit.* FROM unit,chain_unit_relation" +
            " WHERE chain_unit_relation.type = 0 AND" +
            " AND unit.id = chain_unit_relation.unit_id AND chain_unit_relation.chain_id = #{chainId}")
    List<Unit> selectByChainId(@Param(value = "chainId") Integer chainId);

}

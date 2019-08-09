package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.UnitGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangyibo
 * @title: UnitGroupMapper
 * @projectName aihulk
 * @description: UnitGroupMapper
 * @date 2019-06-0314:11
 */
public interface UnitGroupMapper extends BaseMapper<UnitGroup> {

    @Select(value = "SELECT ug.* FROM unit_group as ug,chain_unit_relation cur WHERE" +
            " cur.type = 1 AND" +
            " ug.id = cur.unit_id AND" +
            " ug.chain_id = #{chainId}")
    List<UnitGroup> selectByChainId(@Param(value = "chainId") Integer chainId);

}

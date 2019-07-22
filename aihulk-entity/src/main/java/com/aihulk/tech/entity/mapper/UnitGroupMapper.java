package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.UnitGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    @Select(value = "SELECT * FROM unit_unit_group_relation")
    List<Map<String, Object>> selectUnitUnitGroupRelation();

    @Insert(value = "INSERT INTO unit_unit_group_relation (unit_id,unit_group_id,deleted) VALUE (#{unitId},#{unitGroupId},0)")
    int insertUnitGroupUnitRelation(@Param(value = "unitId") Integer unitId, @Param(value = "unitGroupId") Integer unitGroupId);

}

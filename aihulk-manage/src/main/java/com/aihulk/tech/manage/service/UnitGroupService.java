package com.aihulk.tech.manage.service;

import com.aihulk.tech.entity.entity.UnitGroup;
import com.aihulk.tech.entity.mapper.UnitGroupMapper;
import org.springframework.stereotype.Service;

/**
 * @author zhangyibo
 * @title: UnitGroupService
 * @projectName aihulk
 * @description: UnitGroupService
 * @date 2019-06-2815:45
 */
@Service
public class UnitGroupService extends BaseService<UnitGroup, UnitGroupMapper> {

    public void insertUnitGroupUnitRelation(Integer unitId, Integer unitGroupId) {
        baseMapper.insertUnitGroupUnitRelation(unitId, unitGroupId);
    }

}

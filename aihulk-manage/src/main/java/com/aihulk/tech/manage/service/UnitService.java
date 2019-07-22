package com.aihulk.tech.manage.service;

import com.aihulk.tech.entity.entity.Unit;
import com.aihulk.tech.entity.entity.UnitGroup;
import com.aihulk.tech.entity.mapper.UnitMapper;
import com.aihulk.tech.manage.exception.ManageException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.aihulk.tech.manage.vo.base.BaseResponseVo.ManageBusinessErrorCode.UNIT_GROUP_NOT_EXIST;

/**
 * @author zhangyibo
 * @title: UnitService
 * @projectName aihulk
 * @description: UnitService
 * @date 2019-06-2815:45
 */
@Slf4j
@Service
public class UnitService extends BaseService<Unit, UnitMapper> {

    @Autowired
    private UnitGroupService unitGroupService;

    @Transactional(rollbackFor = Exception.class)
    public void addUnit(Unit unit, Integer groupId) {
        UnitGroup unitGroup = unitGroupService.selectOne(new QueryWrapper<UnitGroup>().lambda().eq(UnitGroup::getId, groupId));
        if (unitGroup == null) {
            log.error("unit group does not exist,groupId = {}", groupId);
            throw new ManageException(UNIT_GROUP_NOT_EXIST, "执行单元组不存在，unitGroupId = " + groupId);
        }
        baseMapper.insert(unit);
        unitGroupService.insertUnitGroupUnitRelation(unit.getId(), groupId);
    }

}

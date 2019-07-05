package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.entity.Action;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author zhangyibo
 * @title: ActionMapper
 * @projectName aihulk
 * @description: ActionMapper
 * @date 2019-07-0514:02
 */
public interface ActionMapper extends BaseMapper<Action> {

    List<Action> selectByUnitId(Integer unitId);

}

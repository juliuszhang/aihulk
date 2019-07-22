package com.aihulk.tech.manage.vo;

import com.aihulk.tech.entity.entity.Action;
import com.aihulk.tech.entity.entity.Logic;
import com.aihulk.tech.entity.entity.Unit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author zhangyiboø
 * @title: UnitVo
 * @projectName aihulk
 * @description: UnitVo
 * @date 2019-07-2217:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UnitVo extends Unit {

    private List<Action> actions;

    private Logic logic;

    public UnitVo(Unit unit, List<Action> actions, Logic logic) {
        BeanUtils.copyProperties(unit, this);
        this.actions = actions;
        this.logic = logic;
    }
}

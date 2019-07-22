package com.aihulk.tech.manage.controller;

import com.aihulk.tech.entity.entity.Action;
import com.aihulk.tech.entity.entity.Logic;
import com.aihulk.tech.entity.entity.Unit;
import com.aihulk.tech.manage.service.ActionService;
import com.aihulk.tech.manage.service.LogicService;
import com.aihulk.tech.manage.service.UnitService;
import com.aihulk.tech.manage.vo.UnitVo;
import com.aihulk.tech.manage.vo.base.BaseResponseVo;
import com.aihulk.tech.manage.vo.base.ResponsePageVo;
import com.aihulk.tech.manage.vo.base.ResponseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: UnitController
 * @projectName aihulk
 * @description: unit
 * @date 2019-07-0414:13
 */
@RestController
@RequestMapping(value = "/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private LogicService logicService;

    @PostMapping(value = "/{unitGroupId}")
    public ResponseVo<Void> add(@PathVariable(value = "unitGroupId") Integer unitGroupId, @RequestBody Unit unit) {
        unitService.addUnit(unit, unitGroupId);
        return new ResponseVo<Void>().buildSuccess("添加成功");
    }

    @GetMapping(value = "")
    public BaseResponseVo<List<UnitVo>> select(@RequestParam(required = false) Integer start,
                                               @RequestParam(required = false) Integer pageSize,
                                               Unit unit) {
        if (start != null && start > 0 && pageSize != null && pageSize > 0) {
            IPage<Unit> iPage = unitService.selectPage(unit, new Page<>(start, pageSize));
            List<Unit> units = iPage.getRecords();
            List<UnitVo> unitVos = this.map(units);
            return new ResponsePageVo<UnitVo>().buildSuccess(unitVos, "ok", iPage.getCurrent(), iPage.getSize());
        } else {
            List<Unit> units = unitService.select(unit);
            List<UnitVo> unitVos = this.map(units);
            return new ResponseVo<List<UnitVo>>().buildSuccess(unitVos, "ok");
        }
    }

    private List<UnitVo> map(List<Unit> units) {
        //1.select actions
        List<Integer> unitIds = units.stream().map(Unit::getId).collect(Collectors.toList());
        List<Action> actions = actionService.select(new QueryWrapper<Action>().lambda().in(Action::getUnitId, unitIds));
        final Map<Integer, List<Action>> actionMap = actions.stream().collect(Collectors.groupingBy(Action::getUnitId));
        //2.select logic
        List<Logic> logics = logicService.select(new QueryWrapper<Logic>().lambda().in(Logic::getRelationId, unitIds));
        Map<Integer, List<Logic>> logicMap = logics.stream().collect(Collectors.groupingBy(Logic::getRelationId));
        List<UnitVo> unitVos = units.stream().map(unit -> new UnitVo(unit,
                actionMap.get(unit.getId()),
                logicMap.get(unit.getId()).get(0))).collect(Collectors.toList());
        return unitVos;
    }
}

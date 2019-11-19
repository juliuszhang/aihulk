package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.logic.ExpressHelper;
import com.aihulk.tech.core.resource.entity.DecisionBlock;
import com.aihulk.tech.core.resource.entity.DecisionTable;
import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.Fact;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.entity.entity.Logic;
import com.aihulk.tech.entity.entity.Unit;
import com.aihulk.tech.entity.entity.UnitFactRelation;
import com.aihulk.tech.entity.mapper.FactRelationMapper;
import com.aihulk.tech.entity.mapper.LogicMapper;
import com.aihulk.tech.entity.mapper.UnitFactRelationMapper;
import com.aihulk.tech.entity.mapper.UnitMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: UnitResourceLoader
 * @projectName aihulk
 * @description: UnitResourceLoader
 * @date 2019-06-2819:01
 */
@Component
public class UnitResourceLoader implements ResourceLoader<Map<Integer, ExecuteUnit>> {

    @Autowired
    private FactResourceLoader factResourceLoader;

    @Autowired
    private ActionResourceLoader actionResourceLoader;

    @Autowired
    private UnitMapper mapper;

    @Autowired
    private LogicMapper logicMapper;

    @Autowired
    private UnitFactRelationMapper unitFactRelationMapper;

    @Autowired
    private FactRelationMapper factRelationMapper;

    @Override
    public Map<Integer, ExecuteUnit> loadResource(Integer bizId, String version) {
        Unit queryParam = new Unit();
        queryParam.setBusinessId(bizId);
        QueryWrapper wrapper = new QueryWrapper(queryParam);
        List<Unit> units = mapper.selectList(wrapper);
        final List<Fact> allFacts = factResourceLoader.loadResource(bizId, version);
        Map<Integer, Fact> factMap = allFacts.stream().collect(Collectors.toMap(Fact::getId, Function.identity()));
        Map<Integer, List<Fact>> unitFactMap = loadUnitFactRelation(factMap);
        Map<Integer, List<Action>> actionMap = actionResourceLoader.loadResource(bizId, version);
        List<ExecuteUnit> executeUnits = units.stream().map(unit -> this.mapExecuteUnit(unit, unitFactMap, allFacts, actionMap)).collect(Collectors.toList());
        return executeUnits.stream().collect(Collectors.toMap(ExecuteUnit::getId, Function.identity()));
    }

    private Map<Integer, List<Fact>> loadUnitFactRelation(Map<Integer, Fact> factMap) {
        Map<Integer, List<Fact>> resultMap = Maps.newHashMap();
        List<UnitFactRelation> unitFactRelations = unitFactRelationMapper.selectList(new QueryWrapper<>());
        for (UnitFactRelation unitFactRelation : unitFactRelations) {
            Integer factId = unitFactRelation.getFactId();
            Integer unitId = unitFactRelation.getUnitId();
            resultMap.putIfAbsent(unitId, new ArrayList<>());
            resultMap.get(unitId).add(factMap.get(factId));
        }
        return resultMap;
    }

    @Override
    public Map<String, Map<Integer, ExecuteUnit>> loadAllResources(Integer bizId) {
        return null;
    }

    private ExecuteUnit mapExecuteUnit(Unit unit, Map<Integer, List<Fact>> unitFactMap, List<Fact> allFact, Map<Integer, List<Action>> actionMap) {
        if (Unit.TYPE_DECISION_BLOCK.equals(unit.getType())) {
            DecisionBlock executeUnit = new DecisionBlock();
            executeUnit.setName(unit.getName());
            executeUnit.setNameEn(unit.getNameEn());
            List<Logic> logics = logicMapper.selectList(new QueryWrapper<>());
            Logic logic = this.selectLogicById(logics, unit.getId());
            executeUnit.setExpress(ExpressHelper.parse(logic.getLogicExp()));
            List<Fact> runtimeFacts = unitFactMap.get(unit.getId());
            if (runtimeFacts != null) {
                Map<Integer, List<Fact>> relations = Maps.newHashMap();
                this.queryAllFactRelations(runtimeFacts, relations, allFact);
                executeUnit.setFactsWithSort(runtimeFacts, relations);
            }
            List<Action> actions = actionMap.get(unit.getId());
            executeUnit.setActions(actions);
            executeUnit.setId(unit.getId());
            return executeUnit;
        } else if (Unit.TYPE_DECISION_TABLE.equals(unit.getType())) {
            DecisionTable decisionTable = new DecisionTable();
            decisionTable.setRows(null);
            decisionTable.setCols(null);
            decisionTable.setCellMap(null);
            return decisionTable;
        } else {
            // throw exception
        }

        return null;
    }

    private void queryAllFactRelations(List<Fact> facts, Map<Integer, List<Fact>> relations, List<Fact> allFacts) {
        if (facts.isEmpty()) return;
        for (Fact fact : facts) {
            List<com.aihulk.tech.entity.entity.FactRelation> factRelations = factRelationMapper.selectList(new QueryWrapper<com.aihulk.tech.entity.entity.FactRelation>()
                    .lambda().eq(com.aihulk.tech.entity.entity.FactRelation::getFactId, fact.getId()));
            List<Integer> refFactIds = factRelations.stream().map(com.aihulk.tech.entity.entity.FactRelation::getRefFactId).collect(Collectors.toList());
            List<Fact> refFacts = selectByIds(allFacts, refFactIds);
            relations.put(fact.getId(), refFacts);
            queryAllFactRelations(refFacts, relations, allFacts);
        }
    }

    private List<Fact> selectByIds(List<Fact> facts, List<Integer> ids) {
        return facts.stream().filter(fact -> ids.contains(fact.getId())).collect(Collectors.toList());
    }

    public Logic selectLogicById(List<Logic> logics, Integer id) {
        List<Logic> logicList = logics.stream().filter(logic -> logic.getRelationId().equals(id)).collect(Collectors.toList());
        if (!logicList.isEmpty()) return logicList.get(0);
        else return null;
    }

}

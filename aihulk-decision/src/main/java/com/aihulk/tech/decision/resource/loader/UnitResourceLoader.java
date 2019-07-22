package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.logic.ExpressHelper;
import com.aihulk.tech.core.resource.entity.DecisionBlock;
import com.aihulk.tech.core.resource.entity.DecisionTable;
import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.Fact;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.aihulk.tech.entity.entity.Logic;
import com.aihulk.tech.entity.entity.Unit;
import com.aihulk.tech.entity.mapper.FactMapper;
import com.aihulk.tech.entity.mapper.LogicMapper;
import com.aihulk.tech.entity.mapper.UnitMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSession;

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
public class UnitResourceLoader implements ResourceLoader<Map<Integer, ExecuteUnit>> {

    private FactResourceLoader factResourceLoader = new FactResourceLoader();

    private ActionResourceLoader actionResourceLoader = new ActionResourceLoader();

    @Override
    public Map<Integer, ExecuteUnit> loadResource(Integer bizId, String version) {
        SqlSession sqlSession = MybatisService.getInstance().getSqlSession();
        UnitMapper mapper = sqlSession.getMapper(UnitMapper.class);
        Unit queryParam = new Unit();
        queryParam.setBizId(bizId);
        QueryWrapper wrapper = new QueryWrapper(queryParam);
        List<Unit> units = mapper.selectList(wrapper);
        final List<Fact> allFacts = factResourceLoader.loadResource(bizId, version);
        Map<Integer, List<Action>> actionMap = actionResourceLoader.loadResource(bizId, version);
        List<ExecuteUnit> executeUnits = units.stream().map(unit -> this.mapExecuteUnit(unit, allFacts, actionMap)).collect(Collectors.toList());
        return executeUnits.stream().collect(Collectors.toMap(ExecuteUnit::getId, Function.identity()));
    }

    @Override
    public Map<String, Map<Integer, ExecuteUnit>> loadAllResources(Integer bizId) {
        return null;
    }

    private ExecuteUnit mapExecuteUnit(Unit unit, List<Fact> facts, Map<Integer, List<Action>> actionMap) {
        if (Unit.TYPE_DECISION_BLOCK == unit.getType()) {
            DecisionBlock executeUnit = new DecisionBlock();
            executeUnit.setName(unit.getName());
            executeUnit.setNameEn(unit.getNameEn());
            SqlSession sqlSession = MybatisService.getInstance().getSqlSession();
            LogicMapper logicMapper = sqlSession.getMapper(LogicMapper.class);
            List<Logic> logics = logicMapper.selectList(new QueryWrapper<>());
            Logic logic = this.selectLogicById(logics, unit.getId());
            executeUnit.setExpress(ExpressHelper.parse(logic.getLogicExp()));
            List<Fact> runtimeFacts = this.selectByUnitId(facts, unit.getId());
            Map<Integer, List<Fact>> relations = Maps.newHashMap();
            this.queryAllFactRelations(runtimeFacts, relations, facts);
            executeUnit.setFactsWithSort(runtimeFacts, relations);
            List<Action> actions = actionMap.get(unit.getId());
            executeUnit.setActions(actions);
            return executeUnit;
        } else if (Unit.TYPE_DECISION_TABLE == unit.getType()) {
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
        SqlSession sqlSession = MybatisService.getInstance().getSqlSession();
        FactMapper mapper = sqlSession.getMapper(FactMapper.class);
        for (Fact fact : facts) {
            List<Integer> refFactIds = mapper.selectRefFacts(fact.getId());
            List<Fact> refFacts = selectByIds(allFacts, refFactIds);
            relations.put(fact.getId(), refFacts);
            queryAllFactRelations(refFacts, relations, allFacts);
        }
    }


    private List<Fact> selectByUnitId(List<Fact> facts, Integer unitId) {
        return facts.stream().filter(fact -> fact.getUnitId().equals(unitId)).collect(Collectors.toList());
    }

    private List<Fact> selectByIds(List<Fact> facts, List<Integer> ids) {
        return facts.stream().filter(fact -> ids.contains(fact.getId())).collect(Collectors.toList());
    }

    public Logic selectLogicById(List<Logic> logics, Integer id) {
        List<Logic> logicList = logics.stream().filter(logic -> logic.getRelationId() == id).collect(Collectors.toList());
        if (!logicList.isEmpty()) return logicList.get(0);
        else return null;
    }

}

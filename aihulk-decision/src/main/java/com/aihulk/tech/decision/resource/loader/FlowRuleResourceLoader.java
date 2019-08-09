package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.UnitType;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.logic.Express;
import com.aihulk.tech.core.logic.ExpressHelper;
import com.aihulk.tech.core.resource.entity.BasicUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnitGroup;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.entity.entity.FlowRule;
import com.aihulk.tech.entity.entity.Logic;
import com.aihulk.tech.entity.mapper.FlowRuleMapper;
import com.aihulk.tech.entity.mapper.LogicMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: FlowRuleResourceLoader
 * @projectName aihulk
 * @description: FlowRuleResourceLoader
 * @date 2019-07-0115:26
 */
@Component
public class FlowRuleResourceLoader implements ResourceLoader<Map<Integer, List<FlowRuleResourceLoader.FlowRuleBo>>> {

    @Autowired
    private UnitResourceLoader unitResourceLoader = new UnitResourceLoader();

    @Autowired
    private UnitGroupResourceLoader unitGroupResourceLoader = new UnitGroupResourceLoader();

    @Autowired
    private FlowRuleMapper mapper;

    @Autowired
    private LogicMapper logicMapper;

    /**
     * @param bizId
     * @param version 可以根据不同version加载不同版本的资源
     * @return Map.key = chainId Map.value conditionEdges
     */
    @Override
    public Map<Integer, List<FlowRuleBo>> loadResource(Integer bizId, String version) {
        FlowRule queryParam = new FlowRule();
        queryParam.setBizId(bizId);
        QueryWrapper wrapper = new QueryWrapper(queryParam);
        List<FlowRule> flowRules = mapper.selectList(wrapper);
        Map<Integer, ExecuteUnitGroup> unitGroupMap = unitGroupResourceLoader.loadResource(bizId, version);
        Map<Integer, ExecuteUnit> unitMap = unitResourceLoader.loadResource(bizId, version);
        return this.map(flowRules, unitGroupMap, unitMap);
    }

    @Override
    public Map<String, Map<Integer, List<FlowRuleBo>>> loadAllResources(Integer bizId) {
        return null;
    }

    static class FlowRuleBo {
        BasicUnit src;
        BasicUnit dest;
        Express express;
    }

    public Map<Integer, List<FlowRuleBo>> map(List<FlowRule> flowRules, Map<Integer, ExecuteUnitGroup> unitGroupMap, Map<Integer, ExecuteUnit> unitMap) {
        Map<Integer, List<FlowRuleBo>> resultMap = Maps.newHashMap();
        for (FlowRule flowRule : flowRules) {
            Logic logic = logicMapper.selectOne(new QueryWrapper<Logic>().lambda().eq(Logic::getStructure, Logic.STRUCTURE_FLOW_RULE)
                    .eq(Logic::getRelationId, flowRule.getId()));
            Integer srcId = flowRule.getSrcId();
            Integer destId = flowRule.getDestId();
            BasicUnit src;
            BasicUnit dest;
            if (flowRule.getSrcType() == UnitType.EXECUTE_UNIT.getVal()) {
                src = unitMap.get(srcId);
            } else if (flowRule.getSrcType() == UnitType.EXECUTE_UNIT_GROUP.getVal()) {
                src = unitGroupMap.get(srcId);
            } else {
                throw new EngineInitException("未知的执行单元类型srcType = " + flowRule.getSrcType());
            }

            if (flowRule.getDestType() == UnitType.EXECUTE_UNIT.getVal()) {
                dest = unitMap.get(destId);
            } else if (flowRule.getDestType() == UnitType.EXECUTE_UNIT_GROUP.getVal()) {
                dest = unitGroupMap.get(destId);
            } else {
                throw new EngineInitException("未知的执行单元类型destType = " + flowRule.getDestType());
            }

            FlowRuleBo flowRuleBo = new FlowRuleBo();
            flowRuleBo.src = src;
            flowRuleBo.dest = dest;
            flowRuleBo.express = ExpressHelper.parse(logic.getLogicExp());

            resultMap.putIfAbsent(flowRule.getChainId(), new ArrayList<>());
            resultMap.get(flowRule.getChainId()).add(flowRuleBo);
        }

        return resultMap;
    }
}

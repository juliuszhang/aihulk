package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.entity.FlowRule;
import com.aihulk.tech.common.mapper.FlowRuleMapper;
import com.aihulk.tech.core.exception.EngineInitException;
import com.aihulk.tech.core.logic.LogicHelper;
import com.aihulk.tech.core.resource.entity.BasicUnit;
import com.aihulk.tech.core.resource.entity.DecisionChain;
import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnitGroup;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: FlowRuleResourceLoader
 * @projectName aihulk
 * @description: TODO
 * @date 2019-07-0115:26
 */
public class FlowRuleResourceLoader implements ResourceLoader<Map<Integer, List<DecisionChain.ConditionEdge>>> {

    private UnitResourceLoader unitResourceLoader = new UnitResourceLoader();

    private UnitGroupResourceLoader unitGroupResourceLoader = new UnitGroupResourceLoader();

    /**
     * @param bizId
     * @param version 可以根据不同version加载不同版本的资源
     * @return Map.key = chainId Map.value conditionEdges
     */
    @Override
    public Map<Integer, List<DecisionChain.ConditionEdge>> loadResource(Integer bizId, String version) {
        SqlSession sqlSession = MybatisService.getInstance().getSqlSession();
        FlowRuleMapper mapper = sqlSession.getMapper(FlowRuleMapper.class);
        FlowRule queryParam = new FlowRule();
        queryParam.setBizId(bizId);
        QueryWrapper wrapper = new QueryWrapper(queryParam);
        List<FlowRule> flowRules = mapper.selectList(wrapper);
        Map<Integer, ExecuteUnitGroup> unitGroupMap = unitGroupResourceLoader.loadResource(bizId, version);
        Map<Integer, ExecuteUnit> unitMap = unitResourceLoader.loadResource(bizId, version);
        return this.map(flowRules, unitGroupMap, unitMap);
    }

    @Override
    public Map<String, Map<Integer, List<DecisionChain.ConditionEdge>>> loadAllResources(Integer bizId) {
        return null;
    }

    public Map<Integer, List<DecisionChain.ConditionEdge>> map(List<FlowRule> flowRules, Map<Integer, ExecuteUnitGroup> unitGroupMap, Map<Integer, ExecuteUnit> unitMap) {
        Map<Integer, List<DecisionChain.ConditionEdge>> resultMap = Maps.newHashMap();
        for (FlowRule flowRule : flowRules) {
            DecisionChain.ConditionEdge conditionEdge = new DecisionChain().new ConditionEdge();
            conditionEdge.setLogic(LogicHelper.parse(flowRule.getExpress()));
            Integer srcId = flowRule.getSrcId();
            Integer destId = flowRule.getDestId();
            BasicUnit src;
            BasicUnit dest;
            if (flowRule.getSrcType() == BasicUnit.UnitType.EXECUTE_UNIT.getVal()) {
                src = unitMap.get(srcId);
            } else if (flowRule.getSrcType() == BasicUnit.UnitType.EXECUTE_UNIT_GROUP.getVal()) {
                src = unitGroupMap.get(srcId);
            } else {
                throw new EngineInitException("未知的执行单元类型srcType = " + flowRule.getSrcType());
            }

            if (flowRule.getDestType() == BasicUnit.UnitType.EXECUTE_UNIT.getVal()) {
                dest = unitMap.get(destId);
            } else if (flowRule.getDestType() == BasicUnit.UnitType.EXECUTE_UNIT_GROUP.getVal()) {
                dest = unitGroupMap.get(destId);
            } else {
                throw new EngineInitException("未知的执行单元类型destType = " + flowRule.getDestType());
            }
            conditionEdge.setSrcBasicUnit(src);
            conditionEdge.setDestBasicUnit(dest);

            resultMap.putIfAbsent(flowRule.getChainId(), new ArrayList<>());
            resultMap.get(flowRule.getChainId()).add(conditionEdge);
        }

        return resultMap;
    }
}

package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.UnitType;
import com.aihulk.tech.core.resource.entity.BasicUnit;
import com.aihulk.tech.core.resource.entity.DecisionChain;
import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnitGroup;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.entity.entity.Chain;
import com.aihulk.tech.entity.mapper.ChainMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: ChainResourceLoader
 * @projectName aihulk
 * @description: ChainResourceLoader
 * @date 2019-06-2818:41
 */
@Component
public class ChainResourceLoader implements ResourceLoader<List<DecisionChain>> {

    private static final int CHAIN_UNIT_RELATION_TYPE_UNIT = UnitType.EXECUTE_UNIT.getVal();
    private static final int CHAIN_UNIT_RELATION_TYPE_UNIT_GROUP = UnitType.EXECUTE_UNIT_GROUP.getVal();

    @Autowired
    private UnitResourceLoader unitResourceLoader;

    @Autowired
    private UnitGroupResourceLoader unitGroupResourceLoader;

    @Autowired
    private FlowRuleResourceLoader flowRuleResourceLoader;

    @Autowired
    private ChainMapper chainMapper;

    @Override
    public List<DecisionChain> loadResource(Integer bizId, String version) {
        SqlSession sqlSession = null;
        try {
            Chain queryParam = new Chain();
            queryParam.setBusinessId(bizId);
            Wrapper wrapper = new QueryWrapper(queryParam);
            List<Chain> chains = chainMapper.selectList(wrapper);
            Map<Integer, ExecuteUnit> executeUnitMap = unitResourceLoader.loadResource(bizId, version);
            Map<Integer, ExecuteUnitGroup> executeUnitGroupMap = unitGroupResourceLoader.loadResource(bizId, version);
            Map<Integer, List<DecisionChain.ConditionEdge>> conditions = flowRuleResourceLoader.loadResource(bizId, version);
            Map<Integer, List<BasicUnit>> chainUnitRelations = getChainUnitRelations(CHAIN_UNIT_RELATION_TYPE_UNIT, executeUnitMap);
            Map<Integer, List<BasicUnit>> chainUnitGroupRelations = getChainUnitRelations(CHAIN_UNIT_RELATION_TYPE_UNIT_GROUP, executeUnitGroupMap);
            return chains.stream().map(chain -> this.map(chain, chainUnitRelations, chainUnitGroupRelations, conditions)).collect(Collectors.toList());
        } finally {
            sqlSession.close();
        }
    }


    public DecisionChain map(Chain chain, Map<Integer, List<BasicUnit>> chainUnitRelations, Map<Integer, List<BasicUnit>> chainUnitGroupRelations, Map<Integer, List<DecisionChain.ConditionEdge>> conditionsMap) {
        DecisionChain decisionChain = new DecisionChain();
        decisionChain.setId(chain.getId());
        decisionChain.setName(chain.getName());
        decisionChain.setNameEn(chain.getNameEn());
        for (BasicUnit basicUnit : chainUnitRelations.get(chain.getId())) {
            decisionChain.add(basicUnit);
        }
        for (BasicUnit basicUnit : chainUnitGroupRelations.get(chain.getId())) {
            decisionChain.add(basicUnit);
        }
        List<DecisionChain.ConditionEdge> conditionEdges = conditionsMap.get(chain.getId());
        decisionChain.add(conditionEdges);
        return decisionChain;
    }


    @Override
    public Map<String, List<DecisionChain>> loadAllResources(Integer bizId) {
        return null;
    }

    private Map<Integer, List<BasicUnit>> getChainUnitRelations(Integer type, Map<Integer, ? extends BasicUnit> allBasicUnits) {
        List<Map<String, Object>> relations = chainMapper.selectUnitChainRelationsByType(type);
        Map<Integer, List<BasicUnit>> relationsMap = Maps.newHashMapWithExpectedSize(relations.size());
        for (Map<String, Object> relation : relations) {
            Integer chainId = (Integer) relation.get("chainId");
            Integer unitId = (Integer) relation.get("unitId");
            relationsMap.putIfAbsent(chainId, new ArrayList<>());
            relationsMap.get(chainId).add(allBasicUnits.get(unitId));
        }
        return relationsMap;
    }
}

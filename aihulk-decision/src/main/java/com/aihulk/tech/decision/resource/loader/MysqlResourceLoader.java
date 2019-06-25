package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.*;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.aihulk.tech.decision.entity.Chain;
import com.aihulk.tech.decision.entity.Unit;
import com.aihulk.tech.decision.entity.UnitGroup;
import com.aihulk.tech.decision.mapper.ChainMapper;
import com.aihulk.tech.decision.mapper.FactMapper;
import com.aihulk.tech.decision.mapper.UnitGroupMapper;
import com.aihulk.tech.decision.mapper.UnitMapper;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: MysqlResourceLoader
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-0414:52
 */
public class MysqlResourceLoader implements ResourceLoader {

    private static final MybatisService MYBATIS_SERVICE = MybatisService.getInstance();


    @Override
    public Resource loadResource(String version) {
        return null;
    }

    @Override
    public Map<String, Resource> loadAllResources() {
        SqlSession sqlSession = MYBATIS_SERVICE.getSqlSession();

        ChainMapper chainMapper = sqlSession.getMapper(ChainMapper.class);
        UnitGroupMapper unitGroupMapper = sqlSession.getMapper(UnitGroupMapper.class);
        UnitMapper unitMapper = sqlSession.getMapper(UnitMapper.class);
        List<Chain> chains = chainMapper.selectAll();
        List<DecisionChain> coreDecisionChains = Lists.newArrayListWithCapacity(chains.size());
        for (Chain chain : chains) {
            DecisionChain coreDecisionChain = new DecisionChain();
            coreDecisionChain.setName(chain.getName());
            coreDecisionChain.setId(chain.getId());
            coreDecisionChain.setNameEn(chain.getNameEn());
            List<UnitGroup> unitGroups = unitGroupMapper.findByDecisionUnitId(chain.getId());
            List<ExecuteUnitGroup> coreExecuteUnitGroups = Lists.newArrayListWithCapacity(unitGroups.size());
            for (UnitGroup unitGroup : unitGroups) {
                ExecuteUnitGroup coreExecuteUnitGroup = new ExecuteUnitGroup();
                coreExecuteUnitGroup.setId(unitGroup.getId());
                coreExecuteUnitGroup.setName(unitGroup.getName());
                coreExecuteUnitGroup.setNameEn(unitGroup.getNameEn());
                List<Unit> units = unitMapper.selectByExecuteUnitGroupId(unitGroup.getId());
                List<ExecuteUnit> coreExecuteUnits = Lists.newArrayListWithCapacity(units.size());
                for (Unit unit : units) {
                    ExecuteUnit coreExecuteUnit = new ExecuteUnit();
                    coreExecuteUnit.setName(unit.getName());
                    coreExecuteUnit.setNameEn(unit.getNameEn());
                    coreExecuteUnit.setFacts(getFeatures(sqlSession, unit.getId()));
                    coreExecuteUnit.setExpress(Express.parse(unit.getExpress()));
//                    coreExecuteUnit.setAction();
                    coreExecuteUnits.add(coreExecuteUnit);
                }
            }


            coreDecisionChains.add(coreDecisionChain);
        }

        return null;
    }


    private List<Fact> getFeatures(SqlSession sqlSession, Integer ruleId) {
        FactMapper factMapper = sqlSession.getMapper(FactMapper.class);
        List<com.aihulk.tech.decision.entity.Fact> facts = factMapper.selectByRuleId(ruleId);
        List<Fact> coreFacts = Lists.newArrayListWithCapacity(facts.size());
        for (com.aihulk.tech.decision.entity.Fact fact : facts) {
            Fact coreFact = new Fact();
            coreFact.setId(fact.getId());
            coreFact.setName(fact.getName());
            coreFact.setNameEn(fact.getNameEn());
            coreFact.setCode(fact.getCode());
            coreFacts.add(coreFact);
        }

        return coreFacts;
    }
}

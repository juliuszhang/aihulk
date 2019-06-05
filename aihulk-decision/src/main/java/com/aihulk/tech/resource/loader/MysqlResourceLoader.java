package com.aihulk.tech.resource.loader;

import com.aihulk.tech.component.MybatisService;
import com.aihulk.tech.entity.Chain;
import com.aihulk.tech.entity.Fact;
import com.aihulk.tech.entity.Unit;
import com.aihulk.tech.entity.UnitGroup;
import com.aihulk.tech.mapper.ChainMapper;
import com.aihulk.tech.mapper.UnitGroupMapper;
import com.aihulk.tech.mapper.FactMapper;
import com.aihulk.tech.mapper.UnitMapper;
import com.aihulk.tech.resource.entity.*;
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
        List<com.aihulk.tech.resource.entity.DecisionChain> coreDecisionChains = Lists.newArrayListWithCapacity(chains.size());
        for (Chain chain : chains) {
            com.aihulk.tech.resource.entity.DecisionChain coreDecisionChain = new com.aihulk.tech.resource.entity.DecisionChain();
            coreDecisionChain.setName(chain.getName());
            coreDecisionChain.setId(chain.getId());
            coreDecisionChain.setNameEn(chain.getNameEn());
            List<UnitGroup> unitGroups = unitGroupMapper.findByDecisionUnitId(chain.getId());
            List<com.aihulk.tech.resource.entity.ExecuteUnitGroup> coreExecuteUnitGroups = Lists.newArrayListWithCapacity(unitGroups.size());
            for (UnitGroup unitGroup : unitGroups) {
                com.aihulk.tech.resource.entity.ExecuteUnitGroup coreExecuteUnitGroup = new com.aihulk.tech.resource.entity.ExecuteUnitGroup();
                coreExecuteUnitGroup.setId(unitGroup.getId());
                coreExecuteUnitGroup.setName(unitGroup.getName());
                coreExecuteUnitGroup.setNameEn(unitGroup.getNameEn());
                List<Unit> units = unitMapper.selectByExecuteUnitGroupId(unitGroup.getId());
                List<com.aihulk.tech.resource.entity.ExecuteUnit> coreExecuteUnits = Lists.newArrayListWithCapacity(units.size());
                for (Unit unit : units) {
                    com.aihulk.tech.resource.entity.ExecuteUnit coreExecuteUnit = new com.aihulk.tech.resource.entity.ExecuteUnit();
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


    private List<com.aihulk.tech.resource.entity.Fact> getFeatures(SqlSession sqlSession, Integer ruleId) {
        FactMapper factMapper = sqlSession.getMapper(FactMapper.class);
        List<Fact> facts = factMapper.selectByRuleId(ruleId);
        List<com.aihulk.tech.resource.entity.Fact> coreFacts = Lists.newArrayListWithCapacity(facts.size());
        for (Fact fact : facts) {
            com.aihulk.tech.resource.entity.Fact coreFact = new com.aihulk.tech.resource.entity.Fact();
            coreFact.setId(fact.getId());
            coreFact.setName(fact.getName());
            coreFact.setNameEn(fact.getNameEn());
            coreFact.setCode(fact.getCode());
            coreFacts.add(coreFact);
        }

        return coreFacts;
    }
}

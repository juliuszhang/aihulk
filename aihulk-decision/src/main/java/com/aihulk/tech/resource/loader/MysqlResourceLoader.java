package com.aihulk.tech.resource.loader;

import com.aihulk.tech.component.MybatisService;
import com.aihulk.tech.entity.DecisionChain;
import com.aihulk.tech.entity.Fact;
import com.aihulk.tech.entity.ExecuteUnit;
import com.aihulk.tech.entity.ExecuteUnitGroup;
import com.aihulk.tech.mapper.DecisionChainMapper;
import com.aihulk.tech.mapper.ExecuteUnitGroupMapper;
import com.aihulk.tech.mapper.FactMapper;
import com.aihulk.tech.mapper.ExecuteUnitMapper;
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

        DecisionChainMapper decisionChainMapper = sqlSession.getMapper(DecisionChainMapper.class);
        ExecuteUnitGroupMapper executeUnitGroupMapper = sqlSession.getMapper(ExecuteUnitGroupMapper.class);
        ExecuteUnitMapper executeUnitMapper = sqlSession.getMapper(ExecuteUnitMapper.class);
        List<DecisionChain> decisionChains = decisionChainMapper.selectAll();
        List<com.aihulk.tech.resource.entity.DecisionChain> coreDecisionChains = Lists.newArrayListWithCapacity(decisionChains.size());
        for (DecisionChain decisionChain : decisionChains) {
            com.aihulk.tech.resource.entity.DecisionChain coreDecisionChain = new com.aihulk.tech.resource.entity.DecisionChain();
            coreDecisionChain.setName(decisionChain.getName());
            coreDecisionChain.setId(decisionChain.getId());
            coreDecisionChain.setNameEn(decisionChain.getNameEn());
            List<ExecuteUnitGroup> executeUnitGroups = executeUnitGroupMapper.findByDecisionUnitId(decisionChain.getId());
            List<com.aihulk.tech.resource.entity.ExecuteUnitGroup> coreExecuteUnitGroups = Lists.newArrayListWithCapacity(executeUnitGroups.size());
            for (ExecuteUnitGroup executeUnitGroup : executeUnitGroups) {
                com.aihulk.tech.resource.entity.ExecuteUnitGroup coreExecuteUnitGroup = new com.aihulk.tech.resource.entity.ExecuteUnitGroup();
                coreExecuteUnitGroup.setId(executeUnitGroup.getId());
                coreExecuteUnitGroup.setName(executeUnitGroup.getName());
                coreExecuteUnitGroup.setNameEn(executeUnitGroup.getNameEn());
                List<ExecuteUnit> executeUnits = executeUnitMapper.selectByExecuteUnitGroupId(executeUnitGroup.getId());
                List<com.aihulk.tech.resource.entity.ExecuteUnit> coreExecuteUnits = Lists.newArrayListWithCapacity(executeUnits.size());
                for (ExecuteUnit executeUnit : executeUnits) {
                    com.aihulk.tech.resource.entity.ExecuteUnit coreExecuteUnit = new com.aihulk.tech.resource.entity.ExecuteUnit();
                    coreExecuteUnit.setName(executeUnit.getName());
                    coreExecuteUnit.setNameEn(executeUnit.getNameEn());
                    coreExecuteUnit.setFacts(getFeatures(sqlSession, executeUnit.getId()));
                    coreExecuteUnit.setExpress(Express.parse(executeUnit.getExpress()));
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

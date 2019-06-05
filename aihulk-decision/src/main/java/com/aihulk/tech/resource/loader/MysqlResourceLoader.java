package com.aihulk.tech.resource.loader;

import com.aihulk.tech.component.MybatisService;
import com.aihulk.tech.entity.DecisionUnit;
import com.aihulk.tech.entity.Feature;
import com.aihulk.tech.entity.Rule;
import com.aihulk.tech.entity.RuleSet;
import com.aihulk.tech.mapper.DecisionUnitMapper;
import com.aihulk.tech.mapper.FeatureMapper;
import com.aihulk.tech.mapper.RuleMapper;
import com.aihulk.tech.mapper.RuleSetMapper;
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

        DecisionUnitMapper decisionUnitMapper = sqlSession.getMapper(DecisionUnitMapper.class);
        RuleSetMapper ruleSetMapper = sqlSession.getMapper(RuleSetMapper.class);
        RuleMapper ruleMapper = sqlSession.getMapper(RuleMapper.class);
        List<DecisionUnit> decisionUnits = decisionUnitMapper.selectAll();
        List<DecisionChain> coreDecisionChains = Lists.newArrayListWithCapacity(decisionUnits.size());
        for (DecisionUnit decisionUnit : decisionUnits) {
            DecisionChain coreDecisionChain = new DecisionChain();
            coreDecisionChain.setName(decisionUnit.getName());
            coreDecisionChain.setId(decisionUnit.getId());
            coreDecisionChain.setNameEn(decisionUnit.getNameEn());
            List<RuleSet> ruleSets = ruleSetMapper.findByDecisionUnitId(decisionUnit.getId());
            List<ExecuteUnitGroup> coreExecuteUnitGroups = Lists.newArrayListWithCapacity(ruleSets.size());
            for (RuleSet ruleSet : ruleSets) {
                ExecuteUnitGroup coreExecuteUnitGroup = new ExecuteUnitGroup();
                coreExecuteUnitGroup.setId(ruleSet.getId());
                coreExecuteUnitGroup.setName(ruleSet.getName());
                coreExecuteUnitGroup.setNameEn(ruleSet.getNameEn());
                List<Rule> rules = ruleMapper.selectByRuleSetId(ruleSet.getId());
                List<ExecuteUnit> coreExecuteUnits = Lists.newArrayListWithCapacity(rules.size());
                for (Rule rule : rules) {
                    ExecuteUnit coreExecuteUnit = new ExecuteUnit();
                    coreExecuteUnit.setName(rule.getName());
                    coreExecuteUnit.setNameEn(rule.getNameEn());
                    coreExecuteUnit.setFacts(getFeatures(sqlSession, rule.getId()));
                    coreExecuteUnit.setExpress(Express.parse(rule.getExpress()));
//                    coreExecuteUnit.setAction();
                    coreExecuteUnits.add(coreExecuteUnit);
                }
            }


            coreDecisionChains.add(coreDecisionChain);
        }

        return null;
    }


    private List<Fact> getFeatures(SqlSession sqlSession, Integer ruleId) {
        FeatureMapper featureMapper = sqlSession.getMapper(FeatureMapper.class);
        List<Feature> features = featureMapper.selectByRuleId(ruleId);
        List<Fact> coreFacts = Lists.newArrayListWithCapacity(features.size());
        for (Feature feature : features) {
            Fact coreFact = new Fact();
            coreFact.setId(feature.getId());
            coreFact.setName(feature.getName());
            coreFact.setNameEn(feature.getNameEn());
            coreFact.setCode(feature.getCode());
            coreFacts.add(coreFact);
        }

        return coreFacts;
    }
}

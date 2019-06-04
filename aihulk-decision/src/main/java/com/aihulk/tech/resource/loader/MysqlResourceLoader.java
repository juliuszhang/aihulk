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
import com.aihulk.tech.resource.entity.Express;
import com.aihulk.tech.resource.entity.Resource;
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
        List<com.aihulk.tech.resource.entity.DecisionUnit> coreDecisionUnits = Lists.newArrayListWithCapacity(decisionUnits.size());
        for (DecisionUnit decisionUnit : decisionUnits) {
            com.aihulk.tech.resource.entity.DecisionUnit coreDecisionUnit = new com.aihulk.tech.resource.entity.DecisionUnit();
            coreDecisionUnit.setName(decisionUnit.getName());
            coreDecisionUnit.setId(decisionUnit.getId());
            coreDecisionUnit.setNameEn(decisionUnit.getNameEn());
            List<RuleSet> ruleSets = ruleSetMapper.findByDecisionUnitId(decisionUnit.getId());
            List<com.aihulk.tech.resource.entity.RuleSet> coreRuleSets = Lists.newArrayListWithCapacity(ruleSets.size());
            for (RuleSet ruleSet : ruleSets) {
                com.aihulk.tech.resource.entity.RuleSet coreRuleSet = new com.aihulk.tech.resource.entity.RuleSet();
                coreRuleSet.setId(ruleSet.getId());
                coreRuleSet.setName(ruleSet.getName());
                coreRuleSet.setNameEn(ruleSet.getNameEn());
                List<Rule> rules = ruleMapper.selectByRuleSetId(ruleSet.getId());
                List<com.aihulk.tech.resource.entity.Rule> coreRules = Lists.newArrayListWithCapacity(rules.size());
                for (Rule rule : rules) {
                    com.aihulk.tech.resource.entity.Rule coreRule = new com.aihulk.tech.resource.entity.Rule();
                    coreRule.setName(rule.getName());
                    coreRule.setNameEn(rule.getNameEn());
                    coreRule.setFeatures(getFeatures(sqlSession, rule.getId()));
                    coreRule.setExpress(Express.parse(rule.getExpress()));
                    coreRule.setAction();
                    coreRules.add(coreRule);
                }
            }


            coreDecisionUnits.add(coreDecisionUnit);
        }
    }


    private List<com.aihulk.tech.resource.entity.Feature> getFeatures(SqlSession sqlSession, Integer ruleId) {
        FeatureMapper featureMapper = sqlSession.getMapper(FeatureMapper.class);
        List<Feature> features = featureMapper.selectByRuleId(ruleId);
        List<com.aihulk.tech.resource.entity.Feature> coreFeatures = Lists.newArrayListWithCapacity(features.size());
        for (Feature feature : features) {
            com.aihulk.tech.resource.entity.Feature coreFeature = new com.aihulk.tech.resource.entity.Feature();
            coreFeature.setId(feature.getId());
            coreFeature.setName(feature.getName());
            coreFeature.setNameEn(feature.getNameEn());
            coreFeature.setCode(feature.getCode());
            coreFeatures.add(coreFeature);
        }

        return coreFeatures;
    }
}

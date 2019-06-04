package com.aihulk.tech.resource.loader;

import com.aihulk.tech.action.JumpToRuleSet;
import com.aihulk.tech.resource.entity.*;
import com.aihulk.tech.util.DateUtil;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 14:40
 * @Description:
 */
public class LocalTestResourceLoader implements ResourceLoader {
    @Override
    public Resource loadResource(String version) {
        Resource resource = new Resource();
        //decision unit
        DecisionUnit unit = new DecisionUnit();
        unit.setId(1);
        unit.setName("测试决策单元");
        unit.setDesc("测试决策单元desc");
        unit.setOperator("yibozhang");
        unit.setCreateTime(DateUtil.getCurDateTime());
        unit.setUpdateTime(DateUtil.getCurDateTime());
        //ruleSets
        RuleSet ruleSet = new RuleSet();
        ruleSet.setId(1);
        ruleSet.setName("测试规则集");
        ruleSet.setDesc("测试规则集desc");
        ruleSet.setOperator("yibozhang4");
        ruleSet.setCreateTime(DateUtil.getCurDateTime());
        ruleSet.setUpdateTime(DateUtil.getCurDateTime());


        //ruleSets2
        RuleSet ruleSet2 = new RuleSet();
        ruleSet2.setId(2);
        ruleSet2.setName("测试规则集2");
        ruleSet2.setDesc("测试规则集2desc");
        ruleSet2.setOperator("yibozhang4");
        ruleSet2.setCreateTime(DateUtil.getCurDateTime());
        ruleSet2.setUpdateTime(DateUtil.getCurDateTime());

        //rules
        Rule rule = new Rule();
        rule.setId(1);
        rule.setName("测试规则");
        rule.setDesc("测试规则desc");
        rule.setCreateTime(DateUtil.getCurDateTime());
        rule.setUpdateTime(DateUtil.getCurDateTime());
        rule.setOperator("yibozhang");
        //feature
        Feature ageFeature = new Feature();
        ageFeature.setId(1);
        ageFeature.setName("获取年龄");
        ageFeature.setCode("function $feature_001(data){ return data.age} \n $feature_001(data);");
        rule.setFeatures(Arrays.asList(ageFeature));
        //express
        Express express = new Express();
        Express subExpress1 = new Express();
        subExpress1.setSrc("$feature_001");
        subExpress1.setTarget(18);
        subExpress1.setOp(Operation.GT);
        Express subExpress2 = new Express();
        subExpress2.setSrc("");
        subExpress2.setOp(Operation.IS_EMPTY);
        express.setSrc(subExpress1);
        express.setTarget(subExpress2);
        express.setOp(Operation.AND);
        rule.setExpress(express);
        rule.setAction(new JumpToRuleSet(ruleSet2.getId()));

        ruleSet.setRules(Arrays.asList(rule));
//        unit.setRuleSets(Arrays.asList(ruleSet,ruleSet2));
        resource.setDecisionUnits(Arrays.asList(unit));
        return resource;
    }

    @Override
    public Map<String, Resource> loadAllResources() {
        return null;
    }
}

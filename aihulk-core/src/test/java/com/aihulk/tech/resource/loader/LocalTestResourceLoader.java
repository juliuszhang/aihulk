package com.aihulk.tech.resource.loader;

import com.aihulk.tech.logic.Operation;
import com.aihulk.tech.resource.entity.*;
import com.aihulk.tech.util.DateUtil;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
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
        DecisionChain chain = new DecisionChain();
        chain.setId(1);
        chain.setName("测试决策单元");
        chain.setDesc("测试决策单元desc");
        chain.setOperator("yibozhang");
        chain.setCreateTime(DateUtil.getCurDateTime());
        chain.setUpdateTime(DateUtil.getCurDateTime());
        //executeUnit
        ExecuteUnit executeUnit1 = new ExecuteUnit();
        executeUnit1.setId(1);
        executeUnit1.setName("测试规则集");
        executeUnit1.setDesc("测试规则集desc");
        executeUnit1.setOperator("yibozhang4");
        executeUnit1.setCreateTime(DateUtil.getCurDateTime());
        executeUnit1.setUpdateTime(DateUtil.getCurDateTime());
        Express executeUnitExpress = new Express();
        executeUnitExpress.setSrc(3);
        executeUnitExpress.setTarget(2);
        executeUnitExpress.setOp(Operation.GT);
        executeUnit1.setExpress(executeUnitExpress);


        //ruleSets2
        ExecuteUnitGroup executeUnitGroup2 = new ExecuteUnitGroup();
        executeUnitGroup2.setId(2);
        executeUnitGroup2.setName("测试规则集2");
        executeUnitGroup2.setDesc("测试规则集2desc");
        executeUnitGroup2.setOperator("yibozhang4");
        executeUnitGroup2.setCreateTime(DateUtil.getCurDateTime());
        executeUnitGroup2.setUpdateTime(DateUtil.getCurDateTime());

        //rules
        ExecuteUnit executeUnit = new ExecuteUnit();
        executeUnit.setId(1);
        executeUnit.setName("测试规则");
        executeUnit.setDesc("测试规则desc");
        executeUnit.setCreateTime(DateUtil.getCurDateTime());
        executeUnit.setUpdateTime(DateUtil.getCurDateTime());
        executeUnit.setOperator("yibozhang");
        //feature
        Fact ageFact = new Fact();
        ageFact.setId(1);
        ageFact.setName("获取年龄");
        ageFact.setCode("function $fact_001(data){ return $ref_fact_002} \n $fact_001(data);");
        Fact refFact = new Fact();
        refFact.setId(2);
        refFact.setName("引用特征");
        refFact.setCode("function $fact_002(data){ return data.age} \n $fact_002(data);");
        Map<Integer, List<Fact>> relation = Maps.newHashMap();
        relation.put(1, Arrays.asList(refFact));
        executeUnit.setFactsWithSort(Arrays.asList(ageFact, refFact), relation);
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
        executeUnit.setExpress(express);

        executeUnitGroup2.setExecuteUnits(Arrays.asList(executeUnit));
        chain.add(executeUnit1);
        chain.add(executeUnitGroup2);
        DecisionChain.ConditionEdge conditionEdge = new DecisionChain.ConditionEdge();
        conditionEdge.setSrcBasicUnit(executeUnit1);
        conditionEdge.setDestBasicUnit(executeUnitGroup2);
        Express flowExpress = new Express();
        flowExpress.setSrc(false);
        flowExpress.setOp(Operation.IS_TRUE);
        conditionEdge.setExpress(flowExpress);
        chain.add(conditionEdge);
        resource.setDecisionChains(Arrays.asList(chain));
        return resource;
    }

    @Override
    public Map<String, Resource> loadAllResources() {
        return null;
    }
}

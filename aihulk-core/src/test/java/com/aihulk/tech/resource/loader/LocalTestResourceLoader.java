package com.aihulk.tech.resource.loader;

import com.aihulk.tech.logic.Operation;
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
        DecisionChain unit = new DecisionChain();
        unit.setId(1);
        unit.setName("测试决策单元");
        unit.setDesc("测试决策单元desc");
        unit.setOperator("yibozhang");
        unit.setCreateTime(DateUtil.getCurDateTime());
        unit.setUpdateTime(DateUtil.getCurDateTime());
        //ruleSets
        ExecuteUnitGroup executeUnitGroup = new ExecuteUnitGroup();
        executeUnitGroup.setId(1);
        executeUnitGroup.setName("测试规则集");
        executeUnitGroup.setDesc("测试规则集desc");
        executeUnitGroup.setOperator("yibozhang4");
        executeUnitGroup.setCreateTime(DateUtil.getCurDateTime());
        executeUnitGroup.setUpdateTime(DateUtil.getCurDateTime());


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
        ageFact.setCode("function $feature_001(data){ return data.age} \n $feature_001(data);");
        executeUnit.setFacts(Arrays.asList(ageFact));
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

        executeUnitGroup.setExecuteUnits(Arrays.asList(executeUnit));
//        unit.setRuleSets(Arrays.asList(executeUnitGroup,executeUnitGroup2));
        resource.setDecisionChains(Arrays.asList(unit));
        return resource;
    }

    @Override
    public Map<String, Resource> loadAllResources() {
        return null;
    }
}

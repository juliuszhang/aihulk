package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.BasicUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnitGroup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: FlowRuleResourceLoaderTest
 * @projectName aihulk
 * @description: FlowRuleResourceLoaderTest
 * @date 2019-08-09 11:26
 */
public class FlowRuleResourceLoaderTest extends AbstractTest {

    @Autowired
    private FlowRuleResourceLoader flowRuleResourceLoader;

    @Test
    public void loadResource() {
        Map<Integer, List<FlowRuleResourceLoader.FlowRuleBo>> resource = flowRuleResourceLoader.loadResource(bizId, null);
        List<FlowRuleResourceLoader.FlowRuleBo> conditionEdges = resource.get(chain.getId());
        Assert.assertEquals(1, conditionEdges.size());
        FlowRuleResourceLoader.FlowRuleBo flowRuleBo = conditionEdges.get(0);
        BasicUnit basicUnit = flowRuleBo.src;
        Assert.assertTrue(basicUnit instanceof ExecuteUnitGroup);
        ExecuteUnitGroup executeUnitGroup = (ExecuteUnitGroup) basicUnit;
        Assert.assertEquals(unitGroup.getName(), executeUnitGroup.getNameEn());
        BasicUnit destBasicUnit = flowRuleBo.dest;
        Assert.assertTrue(destBasicUnit instanceof ExecuteUnit);
        ExecuteUnit executeUnit = (ExecuteUnit) destBasicUnit;
        Assert.assertTrue(executeUnit.getName().equals(unit2.getNameEn()));
    }

}

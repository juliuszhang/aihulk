package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.BasicUnit;
import com.aihulk.tech.core.resource.entity.DecisionChain;
import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnitGroup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhangyibo
 * @title: ChainResourceLoaderTest
 * @projectName aihulk
 * @description: ChainResourceLoaderTest
 * @date 2019-08-09 11:24
 */
public class ChainResourceLoaderTest extends AbstractTest {

    @Autowired
    private ChainResourceLoader chainResourceLoader;

    @Test
    public void loadResource() {
        List<DecisionChain> decisionChains = chainResourceLoader.loadResource(bizId, null);
        DecisionChain dc = decisionChains.stream().filter(decisionChain -> decisionChain.getId().equals(chain.getId())).findFirst().get();
        Iterator<BasicUnit> iterator = dc.iterator();
        BasicUnit bu1 = iterator.next();
        Assert.assertEquals(((ExecuteUnitGroup) bu1).getId(), unitGroup.getId());
        BasicUnit bu2 = iterator.next();
        Assert.assertEquals(((ExecuteUnit) bu2).getId(), unit2.getId());
    }

}

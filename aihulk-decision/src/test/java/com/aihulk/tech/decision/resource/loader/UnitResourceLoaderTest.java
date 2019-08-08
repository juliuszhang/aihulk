package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.Fact;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: UnitResourceLoaderTest
 * @projectName aihulk
 * @description: UnitResourceLoaderTest
 * @date 2019-08-08 17:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UnitResourceLoaderTest extends AbstractTest {

    @Autowired
    private UnitResourceLoader unitResourceLoader;

    @Test
    public void loadResource() {
        Map<Integer, ExecuteUnit> map = unitResourceLoader.loadResource(1, null);
        ExecuteUnit executeUnit = map.get(unit.getId());
        Assert.assertEquals(unit.getName(), executeUnit.getName());
        Assert.assertEquals(unit.getNameEn(), executeUnit.getNameEn());
        List<Fact> facts = executeUnit.getFacts();
        Assert.assertTrue(facts.size() == 2);
        Fact fact1 = facts.get(0);
        Fact fact2 = facts.get(1);
        Assert.assertEquals(super.fact1.getId(), fact1.getId());
        Assert.assertEquals(super.fact2.getId(), fact2.getId());
    }

}

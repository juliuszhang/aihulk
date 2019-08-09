package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.ExecuteUnit;
import com.aihulk.tech.core.resource.entity.ExecuteUnitGroup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: UnitGroupResourceLoader
 * @projectName aihulk
 * @description: UnitGroupResourceLoader
 * @date 2019-08-09 10:47
 */
public class UnitGroupResourceLoaderTest extends AbstractTest {

    @Autowired
    private UnitGroupResourceLoader unitGroupResourceLoader;

    @Test
    public void loadResource() {
        Map<Integer, ExecuteUnitGroup> executeUnitGroupMap = unitGroupResourceLoader.loadResource(bizId, "");
        ExecuteUnitGroup executeUnitGroup = executeUnitGroupMap.get(unitGroup.getId());
        Assert.assertEquals(super.unitGroup.getName(), executeUnitGroup.getName());
        Assert.assertEquals(super.unitGroup.getNameEn(), executeUnitGroup.getNameEn());
        List<ExecuteUnit> executeUnits = executeUnitGroup.getExecuteUnits();
        Assert.assertEquals(1, executeUnits.size());
        ExecuteUnit executeUnit = executeUnits.get(0);
        Assert.assertEquals(unit.getName(), executeUnit.getName());
    }

}

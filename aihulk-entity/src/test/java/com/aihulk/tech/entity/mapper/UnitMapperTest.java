package com.aihulk.tech.entity.mapper;

import com.aihulk.tech.entity.TestHelper;
import com.aihulk.tech.entity.entity.Unit;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhangyibo
 * @title: UnitMapperTest
 * @projectName aihulk
 * @description: UnitMapperTest
 * @date 2019-07-2215:09
 */
public class UnitMapperTest {

    private TestHelper testHelper = new TestHelper();

    private UnitMapper unitMapper = testHelper.getMapper(UnitMapper.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void selectByChainId() {
        List<Unit> units = unitMapper.selectByChainId(1);
    }

    @Test
    public void insert() {
        Unit unit = new Unit();
        unit.setBusinessId(1);
        unit.setName("test");
        unit.setNameEn("test");
        unit.setType(Unit.TYPE_DECISION_BLOCK);
        unitMapper.insert(unit);
    }
}

package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.ScriptCodeType;
import com.aihulk.tech.core.resource.entity.Fact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author zhangyibo
 * @title: FactResourceLoaderTest
 * @projectName aihulk
 * @description: FactResourceLoaderTest
 * @date 2019-08-08 17:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FactResourceLoaderTest extends AbstractTest {

    @Autowired
    private FactResourceLoader factResourceLoader;


    @Test
    public void loadResource() {
        List<Fact> facts = factResourceLoader.loadResource(1, null);
        Fact fact1 = facts.get(facts.size() - 2);
        Fact fact2 = facts.get(facts.size() - 1);
        assertTrue(fact1.getCode().equals(super.fact1.getFormatScript()));
        assertTrue(fact2.getCode().equals(super.fact2.getFormatScript()));
        assertEquals(ScriptCodeType.BASIC, fact1.getCodeType());
        assertEquals(ScriptCodeType.JS, fact2.getCodeType());
        assertEquals(DataType.NUMBER, fact1.getResultType());
        assertEquals(DataType.NUMBER, fact2.getResultType());
    }


}

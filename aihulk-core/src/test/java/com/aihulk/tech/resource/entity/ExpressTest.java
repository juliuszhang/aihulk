package com.aihulk.tech.resource.entity;

import com.aihulk.tech.resource.loader.LocalTestResourceLoader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 15:11
 * @Description:
 */
public class ExpressTest {

    private LocalTestResourceLoader resourceLoader = new LocalTestResourceLoader();

    @Test
    public void eval() {
        Resource resource = resourceLoader.loadResource("");
        Express express = resource.getDecisionUnits().get(0).getRuleSets().get(0).getRules().get(0).getExpress();
        Assert.assertTrue(express.eval());
    }
}
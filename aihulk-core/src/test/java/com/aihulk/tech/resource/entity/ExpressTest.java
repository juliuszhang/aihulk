package com.aihulk.tech.resource.entity;

import com.aihulk.tech.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 15:11
 * @Description:
 */
public class ExpressTest {

    @Test
    public void eval() {
        Express express = new Express();
        express.setSrc(1);
        express.setOp(Operation.GT);
        express.setTarget(2);
        Assert.assertFalse(express.eval());
    }

    @Test
    public void parse() {
        Express express = new Express();
        express.setSrc(1);
        express.setOp(Operation.GT);
        express.setTarget(2);
        String expressStr = JsonUtil.toJsonString(express);
        Express newExpress = Express.parse(expressStr);
        Assert.assertTrue((int) newExpress.getSrc() == 1);
        Assert.assertTrue((int) newExpress.getTarget() == 2);
        Assert.assertTrue(newExpress.getOp() == Operation.GT);

        Express express2 = new Express();
        Express express2Sub1 = new Express();
        express2Sub1.setSrc(3);
        express2Sub1.setOp(Operation.GT);
        express2Sub1.setTarget(2);
        Express express2Sub2 = new Express();
        express2Sub2.setSrc("123");
        express2Sub2.setOp(Operation.EQ);
        express2Sub2.setTarget("123");
        express2.setSrc(express2Sub1);
        express2.setOp(Operation.AND);
        express2.setTarget(express2Sub2);
        Assert.assertTrue(express2.eval());

        String express2Str = JsonUtil.toJsonString(express2);
        Express newExpress2 = Express.parse(express2Str);
        Assert.assertTrue(newExpress2.eval());
        Assert.assertTrue((int) ((Express) newExpress2.getSrc()).getSrc() == 3);

    }
}
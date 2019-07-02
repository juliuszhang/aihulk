package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.core.logic.Express;
import com.aihulk.tech.core.logic.Operation;
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

}

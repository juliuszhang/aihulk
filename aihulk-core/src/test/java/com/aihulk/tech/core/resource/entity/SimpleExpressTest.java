package com.aihulk.tech.core.resource.entity;

import com.aihulk.tech.core.logic.Operation;
import com.aihulk.tech.core.logic.SimpleExpress;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: zhangyibo
 * @Date: 2019/5/2 15:11
 * @Description:
 */
public class SimpleExpressTest {

    @Test
    public void eval() {
        SimpleExpress simpleExpress = new SimpleExpress();
        simpleExpress.setSrc(1);
        simpleExpress.setOp(Operation.GT);
        simpleExpress.setTarget(2);
        Assert.assertFalse(simpleExpress.eval());
    }

}

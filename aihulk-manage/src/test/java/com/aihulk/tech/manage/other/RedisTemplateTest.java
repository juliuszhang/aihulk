package com.aihulk.tech.manage.other;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangyibo
 * @title: RedisTemplateTest
 * @projectName aihulk
 * @description: TODO
 * @date 2019-07-1515:54
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() throws InterruptedException {
        redisTemplate.opsForValue().set("abc", "def");
        String abc = redisTemplate.opsForValue().get("abc");
        Assert.assertEquals(abc, "def");
        redisTemplate.opsForValue().set("expire", "exist", 10, TimeUnit.SECONDS);
        Assert.assertNotNull(redisTemplate.opsForValue().get("expire"));
        Thread.sleep(1000 * 10);
        String expire = redisTemplate.opsForValue().get("expire");
        Assert.assertNull(expire);
    }

}

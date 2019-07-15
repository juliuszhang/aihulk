package com.aihulk.tech.manage.service.msg;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author zhangyibo
 * @title: MessageServiceTest
 * @projectName aihulk
 * @description: MessageServiceTest
 * @date 2019-07-1514:26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void send() {
        Map<String, String> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("check_code", "123456");
        //手机号注册
        messageService.send("18519178873", "爱浩克", "SMS_170110820", paramMap);
    }
}

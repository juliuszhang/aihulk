package com.aihulk.tech.manage.service.msg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangyibo
 * @title: MailServiceTest
 * @projectName aihulk
 * @description: MailServiceTest
 * @date 2019-07-0415:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unit-test")
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMail() {
        mailService.sendMail("xxx@qq.com", "注册邮件1", "222");
    }
}

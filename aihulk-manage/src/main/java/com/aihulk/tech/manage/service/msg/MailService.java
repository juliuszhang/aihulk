package com.aihulk.tech.manage.service.msg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangyibo
 * @title: MailService
 * @projectName aihulk
 * @description: 邮箱
 * @date 2019-07-0415:08
 */
@Component
@Slf4j
public class MailService {

    @Autowired
    private MailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(8);

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            log.error("邮件发送失败 toAddr = {},exception = {}", to, ex.getCause());
        }
    }

    public void sendMailASync(String to, String subject, String text) {
        THREAD_POOL.execute(() -> sendMail(to, subject, text));
    }

}

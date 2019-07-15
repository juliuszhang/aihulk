package com.aihulk.tech.manage.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author zhangyibo
 * @title: KaptchaController
 * @projectName aihulk
 * @description: KaptchaController
 * @date 2019-07-1514:59
 */
@ControllerAdvice
@RequestMapping(value = "kaptcha")
public class KaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @GetMapping(value = "")
    public void getKaptcha() throws IOException {
        String text = defaultKaptcha.createText();
        HttpSession session = request.getSession();
        //将验证码存储在session上下文
        session.setAttribute("kaptcha", text);
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "jpg", sos);
        try {
            sos.flush();
        } finally {
            sos.close();
        }
    }

}

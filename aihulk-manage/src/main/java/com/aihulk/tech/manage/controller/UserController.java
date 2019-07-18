package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.util.MD5Util;
import com.aihulk.tech.common.util.RandomUtil;
import com.aihulk.tech.common.util.RegexUtil;
import com.aihulk.tech.entity.entity.User;
import com.aihulk.tech.manage.constant.LoginType;
import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.service.UserService;
import com.aihulk.tech.manage.service.msg.MailService;
import com.aihulk.tech.manage.service.msg.MessageService;
import com.aihulk.tech.manage.vo.BaseResponseVo;
import com.aihulk.tech.manage.vo.ResponseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhangyibo
 * @title: UserController
 * @projectName aihulk
 * @description: UserController
 * @date 2019-06-2810:59
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String REDIS_CHECK_CODE_KEY_PREFIX = "mobile_";

    //验证码过期时间 1min
    private static final int CHECK_CODE_EXPIRE = 1;

    @Value("${sys-config.enable_email_url}")
    private String enableEmailUrl;

    private static final String SESSION_KEY_USERNAME = "username";
    private static final String SESSION_KEY_TOKEN = "token";

    private static final String SIGN_NAME = "爱浩克";

    private static final String TEMPLATE_CODE = "check_code";


    @GetMapping(value = "/captcha")
    public void getCaptcha() throws IOException {
        String text = defaultKaptcha.createText();
        HttpSession session = request.getSession();
        //将验证码存储在session上下文
        session.setAttribute("captcha", text);
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "jpg", sos);
        try {
            sos.flush();
        } finally {
            sos.close();
        }
    }


    @PostMapping(value = "/regist/{checkCode}")
    public ResponseVo<User> regist(@RequestBody User user, @PathVariable(value = "checkCode") String checkCode) {
        //1.param check
        checkNotNull(user, "user参数不能为空");
        checkArgument(!Strings.isNullOrEmpty(user.getUsername()), "用户名不能为空");
        String email = user.getEmail();
        String mobile = user.getMobile();
        boolean emailIsEmpty = Strings.isNullOrEmpty(email);
        boolean mobileIsEmpty = Strings.isNullOrEmpty(mobile);
        checkArgument(!emailIsEmpty || !mobileIsEmpty, "邮箱|手机号至少填写一个");
        checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "密码不能为空");
        String token = user.initToken();
        //如果是邮箱注册
        if (!emailIsEmpty) {
            if (emailExist(email)) {
                log.warn("email exist,email = {}", email);
                throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_EXIST, "email = " + email + "的用户已存在");
            }
            user.setEmailChecked(User.EMAIL_UNCHECKED);
            StringBuilder builder = new StringBuilder(enableEmailUrl);
            builder.append("?username=").append(user.getUsername());
            builder.append("&token=").append(token);
            mailService.sendMailASync(email, "邮箱激活", builder.toString());
        } else {
            if (mobileExist(mobile)) {
                log.warn("mobile exist,mobile = {}", mobile);
                throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_EXIST, "mobile = " + mobile + "的用户已存在");
            }
            String redisCheckCode = redisTemplate.opsForValue().get(REDIS_CHECK_CODE_KEY_PREFIX + mobile);
            if (redisCheckCode == null || !checkCode.equals(redisCheckCode)) {
                log.warn("checkCode error,redisCheckCode = {},input checkCode = {},mobile = {}", redisCheckCode, checkCode, mobile);
                throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.CHECK_CODE_ERROR, "短信验证码错误");
            }
        }
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        userService.add(user);
        return new ResponseVo<User>().buildSuccess(user, "注册成功");
    }

    /**
     * 获取验证码
     *
     * @param mobile  手机号
     * @param captcha 图片验证码
     * @return
     */
    @GetMapping("/checkCode")
    public ResponseVo<Void> getCheckCode(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "captcha") String captcha) {
        checkArgument(!Strings.isNullOrEmpty(captcha), "验证码不能为空");
        checkArgument(!Strings.isNullOrEmpty(mobile), "手机号不能为空");
        checkArgument(RegexUtil.isMobile(mobile), "手机号格式不合法");
        User user = userService.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile, mobile));
        if (user != null) {
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_EXIST, "该手机号已注册");
        }
        HttpSession session = request.getSession();
        String text = (String) session.getAttribute("captcha");
        if (!captcha.equals(text)) {
            log.warn("text = {},input captcha = {}", text, captcha);
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.KAPTCHA_ERROR, "验证码输入有误");
        }
        if (redisTemplate.hasKey(REDIS_CHECK_CODE_KEY_PREFIX + mobile)) {
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.CHECK_CODE_FETCH_TIMES_FREQUENCY, "验证码获取过于频繁");
        }
        //发送验证码
        Map<String, String> paramMap = Maps.newHashMapWithExpectedSize(1);
        String checkCode = generateCheckCode();
        redisTemplate.opsForValue().set(REDIS_CHECK_CODE_KEY_PREFIX + mobile, checkCode, CHECK_CODE_EXPIRE, TimeUnit.MINUTES);
        log.info("mobile = {},checkCode = {}", mobile, checkCode);
        paramMap.put("check_code", checkCode);
        //手机号注册
        messageService.send(mobile, SIGN_NAME, TEMPLATE_CODE, paramMap);
        return new ResponseVo<Void>().buildSuccess("获取短信验证码成功");
    }

    private boolean mobileExist(String mobile) {
        User user = userService.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile, mobile));
        return user != null;
    }

    private boolean emailExist(String email) {
        User user = userService.selectOne(new QueryWrapper<User>().lambda().eq(User::getEmail, email));
        return user != null;
    }

    private String generateCheckCode() {
        //六位验证码
        return RandomUtil.getNumber(6, 1, 999999, true);
    }

    @GetMapping(value = "/enableEmail")
    public ResponseVo<Void> enableEmail(@RequestParam(value = "username") String username, @RequestParam(value = "token") String token) {
        User queryParam = new User();
        queryParam.setUsername(username);
        User user = userService.selectOne(queryParam);
        if (user == null) {
            throw new ManageException(ResponseVo.ManageBusinessErrorCode.USER_NOT_EXIST, username + "该用户不存在");
        }
        if (!token.equals(user.getToken())) {
            throw new ManageException(ResponseVo.ManageBusinessErrorCode.USER_TOKEN_ERROR, "激活链接有误");
        }

        user.setEmailChecked(User.EMAIL_CHECKED);
        user.initToken();
        return new ResponseVo<Void>().buildSuccess("激活成功");
    }

    @PostMapping(value = "/login")
    public ResponseVo<User> login(@RequestBody Map<String, Object> requestMap) {
        //1.param check
        checkNotNull(requestMap, "请求/user/login的参数不能为空");
        String account = (String) requestMap.get("account");
        checkArgument(!Strings.isNullOrEmpty(account), "用户名不能为空");
        String password = (String) requestMap.get("password");
        checkArgument(!Strings.isNullOrEmpty(password), "密码不能为空");
        //2.query user
        LoginType loginType = LoginType.parse(account);
        User user = queryUser(loginType, account);
        if (user == null) {
            throw new ManageException(ResponseVo.ManageBusinessErrorCode.USER_NOT_EXIST, account + "该用户不存在");
        }
        //3.check email status
        if (loginType == LoginType.EMAIL) {
            if (User.EMAIL_UNCHECKED.equals(user.getEmailChecked())) {
                throw new ManageException(ResponseVo.ManageBusinessErrorCode.USER_EMAIL_UNCHECKED, account + "当前邮箱账户尚未确认");
            }
        }
        //4.check password
        if (!MD5Util.encrypt(password).equals(user.getPassword())) {
            throw new ManageException(ResponseVo.ManageBusinessErrorCode.USER_PASSWORD_WRONG, account + "账户输入密码错误");
        }

        //5.set session attr
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY_USERNAME, user.getUsername());
        String token = refreshToken(user);
        session.setAttribute(SESSION_KEY_TOKEN, token);
        return new ResponseVo<User>().buildSuccess(user, "登录成功");
    }

    @PostMapping(value = "/logout")
    public ResponseVo<Void> logout(@RequestParam String username) {
        checkArgument(!Strings.isNullOrEmpty(username), "username不能为空");
        HttpSession session = request.getSession();
        //clear session
        session.removeAttribute(SESSION_KEY_USERNAME);
        session.removeAttribute(SESSION_KEY_TOKEN);
        return new ResponseVo<Void>().buildSuccess("退出登录成功");
    }

    private String refreshToken(User user) {
        String token = user.initToken();
        userService.update(user);
        return token;
    }

    private User queryUser(LoginType loginType, String account) {
        User queryParam = new User();
        if (loginType == LoginType.EMAIL) {
            queryParam.setEmail(account);
        } else if (loginType == LoginType.MOBILE) {
            queryParam.setMobile(account);
        } else {
            queryParam.setUsername(account);
        }
        return userService.selectOne(queryParam);
    }


}

package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.util.MD5Util;
import com.aihulk.tech.common.util.RandomUtil;
import com.aihulk.tech.common.util.RegexUtil;
import com.aihulk.tech.entity.entity.User;
import com.aihulk.tech.entity.entity.UserInfo;
import com.aihulk.tech.manage.constant.LoginType;
import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.service.UserInfoService;
import com.aihulk.tech.manage.service.UserService;
import com.aihulk.tech.manage.service.msg.MailService;
import com.aihulk.tech.manage.service.msg.MessageService;
import com.aihulk.tech.manage.vo.base.BaseResponseVo;
import com.aihulk.tech.manage.vo.base.ResponseVo;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
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
    private UserInfoService userInfoService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String REDIS_CHECK_CODE_KEY_PREFIX = "mobile_";
    private static final String COOKIE_NAME_CLIENT_ID = "clientId";
    private static final String REDIS_KEY_PIC_CAPTCHA_PREFIX = "PIC_CAPTCHA_";

    //验证码过期时间 1min
    private static final int CHECK_CODE_EXPIRE = 1;

    @Value("${sys_config.enable_email_url}")
    private String enableEmailUrl;

    private static final String COOKEY_NAME_USERNAME = "username";
    private static final String COOKIE_NAME_TOKEN = "token";

    private static final String SIGN_NAME = "爱浩克";

    private static final String TEMPLATE_CODE = "SMS_170110820";

    @GetMapping(value = "/captcha")
    public void getCaptcha() throws IOException {
        String text = defaultKaptcha.createText();
        //将验证码存储在redis中 过期时间为1分钟
        String clientId = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(REDIS_KEY_PIC_CAPTCHA_PREFIX + clientId, text, Duration.ofMinutes(1));
        //将clientId写回cookie 校验图片验证码的时候传回
        writeCookie(COOKIE_NAME_CLIENT_ID, clientId, 60);
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "jpg", sos);
        try {
            sos.flush();
        } finally {
            sos.close();
        }
    }

    private void writeCookie(String key, String value, int expireSecond) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expireSecond);
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);
    }


    @PostMapping(value = "/regist/{checkCode}")
    public ResponseVo<User> regist(@RequestBody User user, @PathVariable(value = "checkCode") String checkCode) {
        //1.param check
        checkNotNull(user, "user参数不能为空");
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
            String builder = enableEmailUrl + "?username=" + user.getUsername() +
                    "&token=" + token;
            mailService.sendMailASync(email, "邮箱激活", builder);
        } else {
            if (mobileExist(mobile)) {
                log.warn("mobile exist,mobile = {}", mobile);
                throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_EXIST, "mobile = " + mobile + "的用户已存在");
            }
            String redisCheckCode = redisTemplate.opsForValue().get(REDIS_CHECK_CODE_KEY_PREFIX + mobile);
            if (!checkCode.equals(redisCheckCode)) {
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
    public ResponseVo<Void> getCheckCode(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "captcha") String captcha, @CookieValue(name = COOKIE_NAME_CLIENT_ID, required = false) String clientId) {
        checkArgument(!Strings.isNullOrEmpty(captcha), "验证码不能为空");
        checkArgument(!Strings.isNullOrEmpty(mobile), "手机号不能为空");
        checkArgument(RegexUtil.isMobile(mobile), "手机号格式不合法");

        if (Strings.isNullOrEmpty(clientId)) {
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.PIC_CAPTCHA_EXPIRED, "图片验证码过期");
        }

        User user = userService.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile, mobile));
        if (user != null) {
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_EXIST, "该手机号已注册");
        }
        String text = redisTemplate.opsForValue().get(REDIS_KEY_PIC_CAPTCHA_PREFIX + clientId);
        if (Strings.isNullOrEmpty(text)) {
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.PIC_CAPTCHA_EXPIRED, "图片验证码过期");
        }
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
//        messageService.send(mobile, SIGN_NAME, TEMPLATE_CODE, paramMap);
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
    public ResponseVo<User> login(@RequestBody User login) {
        //1.param check
        checkNotNull(login, "请求/user/login的参数不能为空");
        String account = login.getUsername();
        checkArgument(!Strings.isNullOrEmpty(account), "用户名不能为空");
        String password = login.getPassword();
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

        //5.write cookie
        writeCookie(COOKEY_NAME_USERNAME, user.getUsername(), 60 * 60 * 24);
        String token = refreshToken(user);
        writeCookie(COOKIE_NAME_TOKEN, token, 60 * 60 * 24);
        return new ResponseVo<User>().buildSuccess(user, "登录成功");
    }

    @PostMapping(value = "/logout")
    public ResponseVo<Void> logout(@RequestParam String username) {
        checkArgument(!Strings.isNullOrEmpty(username), "username不能为空");
        HttpSession session = request.getSession();
        //clear session
        session.removeAttribute(COOKEY_NAME_USERNAME);
        session.removeAttribute(COOKIE_NAME_TOKEN);
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

    @GetMapping(value = "info/{userId}")
    public ResponseVo<UserInfo> getPersonInfo(@PathVariable(value = "userId") int userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        return new ResponseVo<UserInfo>().buildSuccess(userInfoService.selectOne(userInfo), "获取用户个人信息");
    }


}

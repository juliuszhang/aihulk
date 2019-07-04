package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.entity.User;
import com.aihulk.tech.common.util.MD5Util;
import com.aihulk.tech.manage.constant.LoginType;
import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.service.MailService;
import com.aihulk.tech.manage.service.UserService;
import com.aihulk.tech.manage.vo.ResponseVo;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhangyibo
 * @title: UserController
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2810:59
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private HttpServletRequest request;

    @Value("${sys-config.enable_email_url}")
    private String enableEmailUrl;

    private static final String SESSION_KEY_USERNAME = "username";
    private static final String SESSION_KEY_TOKEN = "token";


    @PostMapping(value = "/regist")
    public ResponseVo<User> regist(@RequestBody User user) {
        //1.param check
        checkNotNull(user, "user参数不能为空");
        checkArgument(!Strings.isNullOrEmpty(user.getUsername()), "用户名不能为空");
        boolean emailIsEmpty = Strings.isNullOrEmpty(user.getEmail());
        boolean mobileIsEmpty = Strings.isNullOrEmpty(user.getMobile());
        checkArgument(!emailIsEmpty || !mobileIsEmpty, "邮箱|手机号至少填写一个");
        checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "密码不能为空");
        String token = user.initToken();
        //如果是邮箱注册
        if (!emailIsEmpty) {
            user.setEmailChecked(User.EMAIL_UNCHECKED);
            StringBuilder builder = new StringBuilder(enableEmailUrl);
            builder.append("?username=").append(user.getUsername());
            builder.append("&token=").append(token);
            mailService.sendMail(user.getEmail(), "邮箱激活", builder.toString());
        }
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        userService.add(user);
        return new ResponseVo<User>().buildSuccess(user, "注册成功");
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

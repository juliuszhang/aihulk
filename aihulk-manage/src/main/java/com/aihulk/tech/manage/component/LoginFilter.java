package com.aihulk.tech.manage.component;

import com.aihulk.tech.entity.entity.User;
import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.service.UserService;
import com.aihulk.tech.manage.vo.base.BaseResponseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangyibo
 * @title: LoginFilter
 * @projectName aihulk
 * @description: 校验登录状态
 * @date 2019-07-2214:10
 */
@Component
@Slf4j
public class LoginFilter implements HandlerInterceptor {

    private static final String COOKIE_NAME_USERNAME = "username";
    private static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.error("username is empty. please login");
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_NOT_LOGIN, "用户未登录");
        }
        String username = null;
        String token = null;
        for (Cookie cookie : cookies) {
            if (COOKIE_NAME_USERNAME.equals(cookie.getName())) {
                username = cookie.getValue();
            } else if (COOKIE_NAME_TOKEN.equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }

        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(token)) {
            log.error("username is empty. please login");
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_NOT_LOGIN, "用户未登录");
        }

        User user = userService.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        if (user == null) {
            log.error("username = {} does not exist.", username);
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_NOT_EXIST, "cookie中的用户信息有误");
        }

        if (!token.equals(user.getToken())) {
            log.error("token error. username = {},token = {}", username, token);
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.USER_NOT_LOGIN, "请重新登录");
        }

        return true;
    }
}

package com.aihulk.tech.manage.config;

import com.aihulk.tech.manage.component.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangyibo
 * @title: WebConfig
 * @projectName aihulk
 * @description: WebConfig
 * @date 2019-07-2214:29
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${sys_config.login_filter_enable}")
    private boolean enableLoginFilter;

    @Autowired
    private LoginFilter loginFilter;

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/user/login", "/user/regist/*", "/user/captcha", "/user/checkCode"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (enableLoginFilter) {
            registry.addInterceptor(loginFilter)
                    .addPathPatterns("/**")
                    .excludePathPatterns(EXCLUDE_URLS);
        }
    }
}

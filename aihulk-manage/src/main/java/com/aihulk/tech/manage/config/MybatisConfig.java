package com.aihulk.tech.manage.config;

import com.aihulk.tech.entity.component.SysFieldAutoSetHandler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyibo
 * @title: MybatisConfig
 * @projectName aihulk
 * @description: MybatisConfig
 * @date 2019-06-2719:18
 */
@Configuration
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new SysFieldAutoSetHandler();
    }
}

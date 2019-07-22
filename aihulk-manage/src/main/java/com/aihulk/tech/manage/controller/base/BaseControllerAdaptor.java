package com.aihulk.tech.manage.controller.base;

import com.aihulk.tech.entity.entity.BaseEntity;
import com.aihulk.tech.manage.service.BaseService;

import java.util.List;

/**
 * @author zhangyibo
 * @title: BaseControllerAdaptor
 * @projectName aihulk
 * @description: BaseControllerAdaptor
 * 通常情况下 默认会使用数据库实体 也就是BaseEntity的子类包装结果集返回
 * 因此提供一个快捷方式默认指定查询方法返回BaseEntity的子类 避免重复指定
 * @date 2019-07-2221:57
 */
public class BaseControllerAdaptor<T extends BaseEntity, S extends BaseService<T, ?>> extends BaseController<T, T, S> {

    /**
     * 默认不做转换 原样返回
     *
     * @param ts
     * @return
     */
    @Override
    protected List<T> map(List<T> ts) {
        return ts;
    }
}

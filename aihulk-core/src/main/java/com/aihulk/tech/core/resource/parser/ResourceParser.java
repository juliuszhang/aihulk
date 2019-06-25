package com.aihulk.tech.core.resource.parser;

import com.aihulk.tech.core.resource.entity.Resource;

/**
 * @ClassName ResourceParser
 * @Description 资源解析器
 * @Author yibozhang
 * @Date 2019/5/1 13:28
 * @Version 1.0
 */
public interface ResourceParser<T> {

    Resource parse(T t);

}

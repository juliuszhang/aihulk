package com.aihulk.tech.resource.parser;

import com.aihulk.tech.resource.entity.Resource;
import com.aihulk.tech.util.JsonUtil;

/**
 * @ClassName DefaultResourceParser
 * @Description 默认的资源解析器
 * @Author yibozhang
 * @Date 2019/5/1 13:29
 * @Version 1.0
 */
public class DefaultResourceParser implements ResourceParser<String> {

    @Override
    public Resource parse(String s) {
        return JsonUtil.parseObject(s, Resource.class);
    }
}

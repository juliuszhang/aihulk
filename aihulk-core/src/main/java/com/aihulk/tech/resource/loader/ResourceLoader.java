package com.aihulk.tech.resource.loader;


import com.aihulk.tech.resource.entity.Resource;

import java.util.Map;

/**
 * @ClassName ResourceLoader
 * @Description 加载用于决策的资源
 * @Author yibozhang
 * @Date 2019/2/22 21:25
 * @Version 1.0
 */
public interface ResourceLoader {

    /**
     * 资源加载
     *
     * @param version 可以根据不同version加载不同版本的资源
     * @return
     */
    Resource loadResource(String version);

    /**
     * 一次加载全部资源
     *
     * @return
     */
    Map<String, Resource> loadAllResources();

}

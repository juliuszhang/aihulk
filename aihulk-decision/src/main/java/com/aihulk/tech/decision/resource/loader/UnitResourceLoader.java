package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.entity.Unit;
import com.aihulk.tech.core.resource.loader.ResourceLoader;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: UnitResourceLoader
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2819:01
 */
public class UnitResourceLoader implements ResourceLoader<List<Unit>> {
    @Override
    public List<Unit> loadResource(Integer bizId, String version) {
        return null;
    }

    @Override
    public Map<String, List<Unit>> loadAllResources(Integer bizId) {
        return null;
    }
}

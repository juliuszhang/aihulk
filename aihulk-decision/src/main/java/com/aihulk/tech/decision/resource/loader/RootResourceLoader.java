package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.core.resource.entity.DecisionChain;
import com.aihulk.tech.core.resource.entity.Resource;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.util.SpringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: RootResourceLoader
 * @projectName aihulk
 * @description: RootResourceLoader
 * @date 2019-08-12 15:41
 */
public class RootResourceLoader implements ResourceLoader<Resource> {

    private ChainResourceLoader chainResourceLoader = SpringUtil.getBean(ChainResourceLoader.class);

    @Override
    public Resource loadResource(Integer bizId, String version) {
        List<DecisionChain> decisionChains = chainResourceLoader.loadResource(bizId, version);
        Resource resource = new Resource();
        resource.setDecisionChains(decisionChains);
        return resource;
    }

    @Override
    public Map<String, Resource> loadAllResources(Integer bizId) {
        return null;
    }
}

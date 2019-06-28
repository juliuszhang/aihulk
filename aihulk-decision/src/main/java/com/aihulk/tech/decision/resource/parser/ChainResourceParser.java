package com.aihulk.tech.decision.resource.parser;

import com.aihulk.tech.common.entity.Chain;
import com.aihulk.tech.common.entity.Unit;
import com.aihulk.tech.core.resource.parser.ResourceParser;
import com.aihulk.tech.decision.resource.loader.UnitResourceLoader;

import java.util.List;

/**
 * @author zhangyibo
 * @title: ChainResourceParser
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2818:46
 */
public class ChainResourceParser implements ResourceParser<List<Chain>> {

    private UnitResourceLoader unitResourceLoader = new UnitResourceLoader();

    @Override
    public List<Chain> parse(List<Chain> chains, Integer bizId, String version) {
        List<Unit> units = unitResourceLoader.loadResource(bizId, version);
        //TODO
        return chains;
    }
}

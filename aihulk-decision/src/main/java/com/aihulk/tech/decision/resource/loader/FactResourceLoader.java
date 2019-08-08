package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.DataType;
import com.aihulk.tech.common.constant.ScriptCodeType;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.entity.entity.Fact;
import com.aihulk.tech.entity.mapper.FactMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: FactResourceLoader
 * @projectName aihulk
 * @description: FactResourceLoader
 * @date 2019-07-0110:29
 */
@Component
public class FactResourceLoader implements ResourceLoader<List<com.aihulk.tech.core.resource.entity.Fact>> {

    @Autowired
    private FactMapper mapper;

    @Override
    public List<com.aihulk.tech.core.resource.entity.Fact> loadResource(Integer bizId, String version) {
        Fact queryParam = new Fact();
        queryParam.setBusinessId(bizId);
        QueryWrapper<Fact> wrapper = new QueryWrapper<>(queryParam);
        List<Fact> facts = mapper.selectList(wrapper);
        return facts.stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<com.aihulk.tech.core.resource.entity.Fact>> loadAllResources(Integer bizId) {
        return null;
    }


    /**
     * 从数据库结构转换成决策时需要的结构
     *
     * @param fact
     * @return
     */
    public com.aihulk.tech.core.resource.entity.Fact map(Fact fact) {
        com.aihulk.tech.core.resource.entity.Fact runtimeFact = new com.aihulk.tech.core.resource.entity.Fact();
        runtimeFact.setCode(fact.getFormatScript());
        runtimeFact.setName(fact.getName());
        runtimeFact.setNameEn(fact.getNameEn());
        runtimeFact.setCodeType(ScriptCodeType.parse(fact.getScriptType()));
        runtimeFact.setResultType(DataType.parse(fact.getResultType()));
        runtimeFact.setCreateTime(fact.getCreateTime());
        runtimeFact.setUpdateTime(fact.getUpdateTime());
        runtimeFact.setId(fact.getId());
        return runtimeFact;
    }
}

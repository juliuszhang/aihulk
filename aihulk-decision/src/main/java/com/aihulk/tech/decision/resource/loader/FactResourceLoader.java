package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.entity.Fact;
import com.aihulk.tech.common.mapper.FactMapper;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: FactResourceLoader
 * @projectName aihulk
 * @description: TODO
 * @date 2019-07-0110:29
 */
public class FactResourceLoader implements ResourceLoader<List<com.aihulk.tech.core.resource.entity.Fact>> {

    @Override
    public List<com.aihulk.tech.core.resource.entity.Fact> loadResource(Integer bizId, String version) {
        SqlSession sqlSession = MybatisService.getInstance().getSqlSession();
        FactMapper mapper = sqlSession.getMapper(FactMapper.class);
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
        runtimeFact.setCode(fact.getCode());
        runtimeFact.setName(fact.getName());
        runtimeFact.setNameEn(fact.getNameEn());
        runtimeFact.setCreateTime(fact.getCreateTime());
        runtimeFact.setUpdateTime(fact.getUpdateTime());
        return runtimeFact;
    }
}

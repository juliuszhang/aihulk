package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.entity.Chain;
import com.aihulk.tech.common.mapper.ChainMapper;
import com.aihulk.tech.core.resource.loader.ResourceLoader;
import com.aihulk.tech.decision.component.MybatisService;
import com.aihulk.tech.decision.resource.parser.ChainResourceParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyibo
 * @title: ChainResourceLoader
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2818:41
 */
public class ChainResourceLoader implements ResourceLoader<List<Chain>> {

    private ChainResourceParser chainResourceParser = new ChainResourceParser();

    @Override
    public List<Chain> loadResource(Integer bizId, String version) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisService.getInstance().getSqlSession();
            ChainMapper chainMapper = sqlSession.getMapper(ChainMapper.class);
            Chain queryParam = new Chain();
            queryParam.setBusinessId(bizId);
            Wrapper wrapper = new QueryWrapper(queryParam);
            List<Chain> list = chainMapper.selectList(wrapper);
            return chainResourceParser.parse(list, bizId, version);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public Map<String, List<Chain>> loadAllResources(Integer bizId) {
        return null;
    }
}

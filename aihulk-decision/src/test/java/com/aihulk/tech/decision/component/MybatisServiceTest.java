package com.aihulk.tech.decision.component;

import com.aihulk.tech.entity.entity.Business;
import com.aihulk.tech.entity.entity.Fact;
import com.aihulk.tech.entity.mapper.BusinessMapper;
import com.aihulk.tech.entity.mapper.FactMapper;
import org.junit.Test;

import java.util.List;

public class MybatisServiceTest {

    @Test
    public void getSqlSession() {
        MybatisService mybatisService = MybatisService.getInstance();
        BusinessMapper mapper = mybatisService.getSqlSession().getMapper(BusinessMapper.class);
        List<Business> businesses = mapper.selectList(null);
        System.out.println(businesses);
        FactMapper factMapper = mybatisService.getSqlSession().getMapper(FactMapper.class);
        List<Fact> facts = factMapper.selectList(null);
        System.out.println(facts);

    }
}

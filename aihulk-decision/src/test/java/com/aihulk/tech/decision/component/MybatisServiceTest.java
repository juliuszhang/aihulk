package com.aihulk.tech.decision.component;

import com.aihulk.tech.decision.entity.Business;
import com.aihulk.tech.decision.entity.Fact;
import com.aihulk.tech.decision.mapper.BusinessMapper;
import com.aihulk.tech.decision.mapper.FactMapper;
import org.junit.Test;

import java.util.List;

public class MybatisServiceTest {

    @Test
    public void getSqlSession() {
        MybatisService mybatisService = MybatisService.getInstance();
        BusinessMapper mapper = mybatisService.getSqlSession().getMapper(BusinessMapper.class);
        List<Business> businesses = mapper.selectAll();
        System.out.println(businesses);
        FactMapper factMapper = mybatisService.getSqlSession().getMapper(FactMapper.class);
        List<Fact> facts = factMapper.selectAll();
        System.out.println(facts);

    }
}

package com.aihulk.tech.component;

import com.aihulk.tech.entity.Business;
import com.aihulk.tech.entity.Fact;
import com.aihulk.tech.mapper.BusinessMapper;
import com.aihulk.tech.mapper.FactMapper;
import org.junit.Test;

import java.util.List;

public class MybatisServiceTest {

    @Test
    public void getSqlSession() {

        MybatisService mybatisService = new MybatisService();
        BusinessMapper mapper = mybatisService.getSqlSession().getMapper(BusinessMapper.class);
        List<Business> businesses = mapper.selectAll();
        System.out.println(businesses);
        FactMapper factMapper = mybatisService.getSqlSession().getMapper(FactMapper.class);
        List<Fact> facts = factMapper.selectAll();
        System.out.println(facts);

    }
}
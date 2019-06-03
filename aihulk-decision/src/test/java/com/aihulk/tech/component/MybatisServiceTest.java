package com.aihulk.tech.component;

import com.aihulk.tech.entity.Business;
import com.aihulk.tech.entity.Feature;
import com.aihulk.tech.mapper.BusinessMapper;
import com.aihulk.tech.mapper.FeatureMapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MybatisServiceTest {

    @Test
    public void getSqlSession() {

        MybatisService mybatisService = new MybatisService();
        BusinessMapper mapper = mybatisService.getSqlSession().getMapper(BusinessMapper.class);
        List<Business> businesses = mapper.selectAll();
        System.out.println(businesses);
        FeatureMapper featureMapper = mybatisService.getSqlSession().getMapper(FeatureMapper.class);
        List<Feature> features = featureMapper.selectAll();
        System.out.println(features);

    }
}
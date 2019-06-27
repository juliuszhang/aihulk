package com.aihulk.tech.manage.service;

import com.aihulk.tech.common.entity.Fact;
import com.aihulk.tech.common.mapper.FactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyibo
 * @title: FactService
 * @projectName aihulk
 * @description: FactService
 * @date 2019-06-2616:53
 */
@Service
public class FactService {

    @Autowired
    private FactMapper factMapper;

    public List<Fact> select(Fact fact) {
        return factMapper.select(fact);
    }

    public void add(Fact fact) {
        factMapper.insert(fact);
    }

    public void update(Fact fact) {
        factMapper.updateByPrimaryKeySelective(fact);
    }

    public void delete(Integer id) {
        factMapper.deleteByPrimaryKey(id);
    }

}

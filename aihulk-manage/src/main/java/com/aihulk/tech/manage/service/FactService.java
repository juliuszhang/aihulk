package com.aihulk.tech.manage.service;

import com.aihulk.tech.common.entity.Fact;
import com.aihulk.tech.common.mapper.FactMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        QueryWrapper<Fact> wrapper = new QueryWrapper<>(fact);
        return factMapper.selectList(wrapper);
    }

    public IPage<Fact> selectPage(Fact fact, Page<Fact> page) {
        QueryWrapper<Fact> wrapper = new QueryWrapper<>(fact);
        return factMapper.selectPage(page, wrapper);
    }

    public void add(Fact fact) {
        factMapper.insert(fact);
    }

    public void update(Fact fact) {
        factMapper.updateById(fact);
    }

    public void delete(Integer id) {
        factMapper.deleteById(id);
    }

}

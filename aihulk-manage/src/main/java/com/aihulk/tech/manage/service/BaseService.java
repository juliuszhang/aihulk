package com.aihulk.tech.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhangyibo
 * @title: BaseService
 * @projectName aihulk
 * @description: baseService
 * @date 2019-06-2814:45
 */
public abstract class BaseService<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    public List<T> select(T t) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(t);
        return baseMapper.selectList(wrapper);
    }

    public IPage<T> selectPage(T t, Page<T> page) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(t);
        return baseMapper.selectPage(page, wrapper);
    }

    public void add(T t) {
        baseMapper.insert(t);
    }

    public T selectOne(T t) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(t);
        return baseMapper.selectOne(wrapper);
    }

    public void update(T t) {
        baseMapper.updateById(t);
    }

    public void delete(Integer id) {
        baseMapper.deleteById(id);
    }
}

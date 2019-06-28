package com.aihulk.tech.manage.service;

import com.aihulk.tech.common.entity.User;
import com.aihulk.tech.common.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyibo
 * @title: UserService
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2811:33
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectOne(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        return userMapper.selectOne(wrapper);
    }

    public void udpate(User user) {
        userMapper.updateById(user);
    }

    public void insert(User user) {
        userMapper.insert(user);
    }

}

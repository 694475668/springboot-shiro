package com.ratta.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ratta.domain.User;
import com.ratta.mapper.UserMapper;
import com.ratta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘明
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}

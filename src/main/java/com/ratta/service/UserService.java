package com.ratta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ratta.domain.User;

/**
 * @author 刘明
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    User findByUserName(String username);

}

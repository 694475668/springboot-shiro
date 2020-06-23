package com.ratta.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ratta.domain.User;

/**
 * @author 刘明
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);

}

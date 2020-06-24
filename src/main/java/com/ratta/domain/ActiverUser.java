package com.ratta.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 刘明
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActiverUser {
    /**
     * 令牌
     */
    private String token;
    /**
     * 用户信息
     */
    private User user;
}

package com.ratta.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * @author 刘明
 */
@Data
@ToString
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private String id;

    private String userName;

    private String password;

    private Set<Role> roles;

}

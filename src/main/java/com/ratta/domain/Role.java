package com.ratta.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Role implements Serializable {

    @TableId(type = IdType.AUTO)
    private String id;
    @TableField("test_name")
    private String name;
    @TableField("test_key")
    private String key;

    private Set<Permission> permissions;

}

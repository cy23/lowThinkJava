package com.lowthink.lowThinkJava.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@TableName("sys_user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(exist = true)
    private String username;

    @TableField(exist = true)
    private String password;

    private String email;

    private String fullName;

    private boolean enabled = true;

    @TableField("role_ids")
    private String roleIds;
}
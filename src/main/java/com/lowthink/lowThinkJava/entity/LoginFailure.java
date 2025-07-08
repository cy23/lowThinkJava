package com.lowthink.lowThinkJava.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("login_failure")
public class LoginFailure {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private Integer failureCount = 0;
    private LocalDateTime lastFailureTime;
    private LocalDateTime lockUntil;
}
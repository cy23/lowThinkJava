package com.lowthink.lowThinkJava.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lowthink.lowThinkJava.entity.LoginFailure;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Mapper
public interface LoginFailureRepository extends BaseMapper<LoginFailure> {
    default LoginFailure findByUsername(String username) {
        return selectOne(new QueryWrapper<LoginFailure>().eq("username", username));
    }

    default void updateFailureCount(String username, int failureCount, LocalDateTime lastFailureTime, LocalDateTime lockUntil) {
        LoginFailure loginFailure = findByUsername(username);
        if (loginFailure == null) {
            loginFailure = new LoginFailure();
            loginFailure.setUsername(username);
            loginFailure.setFailureCount(failureCount);
            loginFailure.setLastFailureTime(lastFailureTime);
            loginFailure.setLockUntil(lockUntil);
            insert(loginFailure);
        } else {
            loginFailure.setFailureCount(failureCount);
            loginFailure.setLastFailureTime(lastFailureTime);
            loginFailure.setLockUntil(lockUntil);
            updateById(loginFailure);
        }
    }

    default void resetFailure(String username) {
        LoginFailure loginFailure = findByUsername(username);
        if (loginFailure != null) {
            loginFailure.setFailureCount(0);
            loginFailure.setLastFailureTime(null);
            loginFailure.setLockUntil(null);
            updateById(loginFailure);
        }
    }
}
package com.lowthink.lowThinkJava.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lowthink.lowThinkJava.entity.User;
import org.apache.ibatis.annotations.Select;
import java.util.Optional;

public interface UserRepository extends BaseMapper<User> {
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    Optional<User> findByUsername(String username);
}
package com.lowthink.lowThinkJava.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lowthink.lowThinkJava.entity.User;
import com.lowthink.lowThinkJava.repository.UserRepository;
import com.lowthink.lowThinkJava.service.UserService;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.selectList(new QueryWrapper<>());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.selectById(id));
    }

    @Override
    public User createUser(User user) {
        userRepository.insert(user);
        return user;
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.selectById(id);
        if (StrUtil.isBlankIfStr(user)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setFullName(userDetails.getFullName());
        user.setEnabled(userDetails.isEnabled());
        user.setRoleIds(userDetails.getRoleIds());
        userRepository.updateById(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
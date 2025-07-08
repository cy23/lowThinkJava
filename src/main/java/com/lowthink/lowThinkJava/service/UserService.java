package com.lowthink.lowThinkJava.service;

import com.lowthink.lowThinkJava.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    Optional<User> getUserByUsername(String username);
}
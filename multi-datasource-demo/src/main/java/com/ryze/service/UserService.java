package com.ryze.service;

import com.ryze.domain.User;

import java.util.List;

/**
 * Created by xueLai on 2019/7/11.
 */
public interface UserService {
    void insert(User user);
    void update(User user);
    void delete(Long id);
    List<User> findAll();
}

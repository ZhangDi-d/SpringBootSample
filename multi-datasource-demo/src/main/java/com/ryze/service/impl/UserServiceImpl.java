package com.ryze.service.impl;

import com.ryze.domain.User;
import com.ryze.mapper.UserMapper;
import com.ryze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by xueLai on 2019/7/11.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public  void insert(User user){
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void findAll() {
        userMapper.findAll();
    }

}

package com.ryze.sample.service.impl;

import com.ryze.sample.dao.UserInfoDao;
import com.ryze.sample.entity.UserInfo;
import com.ryze.sample.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}
package com.ssm.demo.service.impl;

import com.ssm.demo.dao.AdminUserMapper;
import com.ssm.demo.entity.AdminUser;
import com.ssm.demo.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xueLai on 2019/9/4.
 */
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    AdminUserMapper adminUserMapper;

    @Override
    public AdminUser getAdminUserByToken(String token) {
        return adminUserMapper.getAdminUserByToken(token);
    }
}

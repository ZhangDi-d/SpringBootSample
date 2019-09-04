package com.ssm.demo.service;

import com.ssm.demo.entity.AdminUser;

/**
 * Created by xueLai on 2019/9/4.
 */
public interface AdminUserService {
    AdminUser getAdminUserByToken(String token);

    AdminUser updateTokenAndLogin(AdminUser user);
}

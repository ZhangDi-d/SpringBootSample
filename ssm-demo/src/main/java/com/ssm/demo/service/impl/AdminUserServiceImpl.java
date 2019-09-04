package com.ssm.demo.service.impl;

import com.ssm.demo.dao.AdminUserMapper;
import com.ssm.demo.entity.AdminUser;
import com.ssm.demo.service.AdminUserService;
import com.ssm.demo.util.MD5Util;
import com.ssm.demo.util.NumberUtil;
import com.ssm.demo.util.SystemUtil;
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

    @Override
    public AdminUser updateTokenAndLogin(AdminUser user) {
        AdminUser adminUser = adminUserMapper.getUserByUsernameAndPassword(user.getUserName(), MD5Util.MD5Encode(user.getPassword(), "utf-8"));
        if (adminUser != null) {
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", adminUser.getId());
            if (adminUserMapper.updateUserToken(adminUser.getId(), token) > 0) {
                //返回数据时带上token
                adminUser.setUserToken(token);
                return adminUser;
            }
        }
        return null;
    }

    private String getNewToken(String sessionId, Long userId) {
        String src = sessionId + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }
}

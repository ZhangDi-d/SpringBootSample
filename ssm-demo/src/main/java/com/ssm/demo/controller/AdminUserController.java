package com.ssm.demo.controller;

import com.ssm.demo.common.Result;
import com.ssm.demo.common.ResultGenerator;
import com.ssm.demo.entity.AdminUser;
import com.ssm.demo.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xueLai on 2019/9/4.
 */
@RestController("/users")
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody AdminUser user) {
        Result result = ResultGenerator.genFailResult("登录失败");
        if (user == null|| StringUtils.isEmpty(user.getUserName())|| StringUtils.isEmpty(user.getPasswordMd5())) {
            result = ResultGenerator.genFailResult("参数错误");
        }
        AdminUser adminUser = adminUserService.loginAndUpdateToken(user);
        if (adminUser != null) {
            result = ResultGenerator.genSuccessResult(adminUser);
        }
        return result;
    }
}

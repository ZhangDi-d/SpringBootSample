package com.ryze.sample.filter;

import com.ryze.sample.entity.SysPermission;
import com.ryze.sample.entity.SysRole;
import com.ryze.sample.entity.UserInfo;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xueLai on 2019/3/1.
 */
@Service("customCallback")
public class CustomCallbackImpl implements CustomCallback {
    private static Logger logger = LoggerFactory.getLogger(CustomCallbackImpl.class);
    @Resource
    private HttpServletResponse response;
    @Override
    public void onAfterLogin(HttpServletRequest servletRequest, String userInfo) {
        logger.info("CustomCallbackImpl============>onAfterLogin");
        servletRequest.getSession().setAttribute("userInfo",userInfo);
        Cookie userCookie = new Cookie("user", userInfo);
        userCookie.setMaxAge(24*60*60);
        response.addCookie(userCookie);
        SysPermission permission = new SysPermission();
        permission.setUrl("userInfo/userAdd");
        permission.setPermission("userInfo:add");
        SysRole sysRole = new SysRole();
        List listper= new ArrayList<SysPermission>();
        listper.add(permission);
        sysRole.setPermissions(listper);
        List<SysRole> roles = new ArrayList<>();
        roles.add(sysRole);
        UserInfo user = new UserInfo();
        user.setRoleList(roles);
        String successUrl = "userInfo/userAdd";
        try {
            WebUtils.issueRedirect(servletRequest,response,successUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  return "redirect:userInfoAdd"; //不是controller

    }
}

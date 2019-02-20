package com.ryze.sample.service;


import com.ryze.sample.entity.UserInfo;


/**
 * <p>
 *  服务类
 * </p>
 */
public interface UserInfoService /*extends IService<UserInfo>*/ {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
}

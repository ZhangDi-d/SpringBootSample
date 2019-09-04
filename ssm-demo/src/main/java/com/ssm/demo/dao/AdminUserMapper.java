package com.ssm.demo.dao;

import com.ssm.demo.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    AdminUser getAdminUserByToken(String token);

    AdminUser getUserByUsernameAndPassword(@Param("userName")String userName, @Param("passwordMD5")String passwordMD5);

    int updateUserToken(@Param("userId") Long userId, @Param("newToken") String newToken);
}
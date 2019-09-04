package com.ssm.demo.dao;

import com.ssm.demo.entity.ClassFour;

public interface ClassFourMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassFour record);

    int insertSelective(ClassFour record);

    ClassFour selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassFour record);

    int updateByPrimaryKey(ClassFour record);
}
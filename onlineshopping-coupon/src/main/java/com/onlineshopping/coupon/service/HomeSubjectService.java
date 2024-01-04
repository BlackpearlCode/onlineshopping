package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.HomeSubject;
public interface HomeSubjectService{


    int deleteByPrimaryKey(Long id);

    int insert(HomeSubject record);

    int insertSelective(HomeSubject record);

    HomeSubject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeSubject record);

    int updateByPrimaryKey(HomeSubject record);

}

package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.HomeSubjectSpu;
public interface HomeSubjectSpuService{


    int deleteByPrimaryKey(Long id);

    int insert(HomeSubjectSpu record);

    int insertSelective(HomeSubjectSpu record);

    HomeSubjectSpu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeSubjectSpu record);

    int updateByPrimaryKey(HomeSubjectSpu record);

}

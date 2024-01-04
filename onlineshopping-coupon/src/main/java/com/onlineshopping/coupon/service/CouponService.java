package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.Coupon;
public interface CouponService{


    int deleteByPrimaryKey(Long id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

}

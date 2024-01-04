package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.CouponHistory;
public interface CouponHistoryService{


    int deleteByPrimaryKey(Long id);

    int insert(CouponHistory record);

    int insertSelective(CouponHistory record);

    CouponHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponHistory record);

    int updateByPrimaryKey(CouponHistory record);

}

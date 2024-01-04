package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.CouponSpuRelation;
public interface CouponSpuRelationService{


    int deleteByPrimaryKey(Long id);

    int insert(CouponSpuRelation record);

    int insertSelective(CouponSpuRelation record);

    CouponSpuRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponSpuRelation record);

    int updateByPrimaryKey(CouponSpuRelation record);

}

package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.MemberPrice;
public interface MemberPriceService{


    int deleteByPrimaryKey(Long id);

    int insert(MemberPrice record);

    int insertSelective(MemberPrice record);

    MemberPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberPrice record);

    int updateByPrimaryKey(MemberPrice record);

}

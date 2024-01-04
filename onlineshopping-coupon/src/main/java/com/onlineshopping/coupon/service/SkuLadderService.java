package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SkuLadder;
public interface SkuLadderService{


    int deleteByPrimaryKey(Long id);

    int insert(SkuLadder record);

    int insertSelective(SkuLadder record);

    SkuLadder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkuLadder record);

    int updateByPrimaryKey(SkuLadder record);

}

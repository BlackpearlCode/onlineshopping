package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SeckillSkuRelation;
public interface SeckillSkuRelationService{


    int deleteByPrimaryKey(Long id);

    int insert(SeckillSkuRelation record);

    int insertSelective(SeckillSkuRelation record);

    SeckillSkuRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSkuRelation record);

    int updateByPrimaryKey(SeckillSkuRelation record);

}

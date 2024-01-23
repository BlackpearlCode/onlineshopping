package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SeckillSkuRelation;

import java.util.List;

public interface SeckillSkuRelationService{


    int deleteByPrimaryKey(Long id);

    int insert(SeckillSkuRelation record);

    int insertSelective(SeckillSkuRelation record);

    SeckillSkuRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSkuRelation record);

    int updateByPrimaryKey(SeckillSkuRelation record);

    List<SeckillSkuRelation> getSkuIdByPromotionSessionId(Long promotionSessionId);
}

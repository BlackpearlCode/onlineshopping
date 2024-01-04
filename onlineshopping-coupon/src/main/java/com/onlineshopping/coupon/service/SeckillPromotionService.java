package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SeckillPromotion;
public interface SeckillPromotionService{


    int deleteByPrimaryKey(Long id);

    int insert(SeckillPromotion record);

    int insertSelective(SeckillPromotion record);

    SeckillPromotion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillPromotion record);

    int updateByPrimaryKey(SeckillPromotion record);

}

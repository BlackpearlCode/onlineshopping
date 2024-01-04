package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SeckillSession;
public interface SeckillSessionService{


    int deleteByPrimaryKey(Long id);

    int insert(SeckillSession record);

    int insertSelective(SeckillSession record);

    SeckillSession selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSession record);

    int updateByPrimaryKey(SeckillSession record);

}

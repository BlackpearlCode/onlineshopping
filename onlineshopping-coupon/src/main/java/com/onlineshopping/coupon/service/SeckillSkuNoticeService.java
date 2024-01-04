package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SeckillSkuNotice;
public interface SeckillSkuNoticeService{


    int deleteByPrimaryKey(Long id);

    int insert(SeckillSkuNotice record);

    int insertSelective(SeckillSkuNotice record);

    SeckillSkuNotice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSkuNotice record);

    int updateByPrimaryKey(SeckillSkuNotice record);

}

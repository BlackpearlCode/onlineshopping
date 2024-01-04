package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SpuBounds;
public interface SpuBoundsService{


    int deleteByPrimaryKey(Long id);

    int insert(SpuBounds record);

    int insertSelective(SpuBounds record);

    SpuBounds selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpuBounds record);

    int updateByPrimaryKey(SpuBounds record);

}

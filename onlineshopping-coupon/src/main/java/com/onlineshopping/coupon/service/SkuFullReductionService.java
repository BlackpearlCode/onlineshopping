package com.onlineshopping.coupon.service;

import com.onlineshopping.coupon.entity.SkuFullReduction;
import com.onlineshopping.common.to.SkuReductionTo;

public interface SkuFullReductionService{


    int deleteByPrimaryKey(Long id);

    int insert(SkuFullReduction record);

    int insertSelective(SkuFullReduction record);

    SkuFullReduction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkuFullReduction record);

    int updateByPrimaryKey(SkuFullReduction record);

    void save(SkuReductionTo skuReductionTo);
}

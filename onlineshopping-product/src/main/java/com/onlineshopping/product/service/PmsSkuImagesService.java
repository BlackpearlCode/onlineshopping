package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsSkuImages;

public interface PmsSkuImagesService{


    int deleteByPrimaryKey(Long id);

    int insert(PmsSkuImages record);

    int insertSelective(PmsSkuImages record);

    PmsSkuImages selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsSkuImages record);

    int updateByPrimaryKey(PmsSkuImages record);

}

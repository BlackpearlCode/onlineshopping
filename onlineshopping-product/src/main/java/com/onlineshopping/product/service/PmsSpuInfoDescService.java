package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsSpuInfoDesc;

public interface PmsSpuInfoDescService{


    int deleteByPrimaryKey(Long spuId);

    int insert(PmsSpuInfoDesc record);

    int insertSelective(PmsSpuInfoDesc record);

    PmsSpuInfoDesc selectByPrimaryKey(Long spuId);

    int updateByPrimaryKeySelective(PmsSpuInfoDesc record);

    int updateByPrimaryKey(PmsSpuInfoDesc record);

}

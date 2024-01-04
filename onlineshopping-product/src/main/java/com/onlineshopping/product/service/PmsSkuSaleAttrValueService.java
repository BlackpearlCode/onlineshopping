package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsSkuSaleAttrValue;
import com.onlineshopping.product.vo.SkuItemSaleAttrVo;

import java.util.List;

public interface PmsSkuSaleAttrValueService{


    int deleteByPrimaryKey(Long id);

    int insert(PmsSkuSaleAttrValue record);

    int insertSelective(PmsSkuSaleAttrValue record);

    PmsSkuSaleAttrValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsSkuSaleAttrValue record);

    int updateByPrimaryKey(PmsSkuSaleAttrValue record);


    List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId);


    List<String> getSkuSaleAttrValuesAsStringList(Long skuId);
}

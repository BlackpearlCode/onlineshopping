package com.onlineshopping.product.service;

import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsSkuInfo;
import com.onlineshopping.product.entity.PmsSpuInfo;
import com.onlineshopping.product.vo.SkuItemVo;
import com.onlineshopping.product.vo.Skus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface PmsSkuInfoService{


    int deleteByPrimaryKey(Long skuId);

    int insert(PmsSkuInfo record);

    int insertSelective(PmsSkuInfo record);

    PmsSkuInfo selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(PmsSkuInfo record);

    int updateByPrimaryKey(PmsSkuInfo record);

    void save(Long id, List<Skus> skus, PmsSpuInfo spuInfo);

    PageEntity getSkuInfo(Map<String, Object> params);

    List<PmsSkuInfo> selectBySpuId(Long spuId);

    // 获取sku的详情信息
    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;

    // 获取spu的销售属性
    List<String> getSkuSaleAtttrValues(Long skuId);

    // 根据skuId获取sku信息
    PmsSkuInfo getSkuInfoBySkuId(Long skuId);
}

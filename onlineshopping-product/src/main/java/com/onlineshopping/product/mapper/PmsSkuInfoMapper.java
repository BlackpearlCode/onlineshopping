package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsSkuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PmsSkuInfoMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(PmsSkuInfo record);

    int insertSelective(PmsSkuInfo record);

    PmsSkuInfo selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(PmsSkuInfo record);

    int updateByPrimaryKey(PmsSkuInfo record);


    List<PmsSkuInfo> getInfo(@Param("params") Map<String, Object> params);

    List<PmsSkuInfo> selectBySpuId(Long spuId);

    PmsSkuInfo getSkuInfoBySkuId(Long skuId);
}
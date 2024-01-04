package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.SkuFullReduction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkuFullReductionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkuFullReduction record);

    int insertSelective(SkuFullReduction record);

    SkuFullReduction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkuFullReduction record);

    int updateByPrimaryKey(SkuFullReduction record);
}
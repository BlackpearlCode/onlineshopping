package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.SpuBounds;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpuBoundsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpuBounds record);

    int insertSelective(SpuBounds record);

    SpuBounds selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpuBounds record);

    int updateByPrimaryKey(SpuBounds record);
}
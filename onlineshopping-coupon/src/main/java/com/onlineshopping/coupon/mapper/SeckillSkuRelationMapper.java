package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.SeckillSkuRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeckillSkuRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillSkuRelation record);

    int insertSelective(SeckillSkuRelation record);

    SeckillSkuRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSkuRelation record);

    int updateByPrimaryKey(SeckillSkuRelation record);
}
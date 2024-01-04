package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.CouponSpuCategoryRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponSpuCategoryRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CouponSpuCategoryRelation record);

    int insertSelective(CouponSpuCategoryRelation record);

    CouponSpuCategoryRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponSpuCategoryRelation record);

    int updateByPrimaryKey(CouponSpuCategoryRelation record);
}
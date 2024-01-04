package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.SeckillSkuNotice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeckillSkuNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillSkuNotice record);

    int insertSelective(SeckillSkuNotice record);

    SeckillSkuNotice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSkuNotice record);

    int updateByPrimaryKey(SeckillSkuNotice record);
}
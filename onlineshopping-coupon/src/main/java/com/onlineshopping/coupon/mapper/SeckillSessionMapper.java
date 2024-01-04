package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.SeckillSession;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeckillSessionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillSession record);

    int insertSelective(SeckillSession record);

    SeckillSession selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSession record);

    int updateByPrimaryKey(SeckillSession record);
}
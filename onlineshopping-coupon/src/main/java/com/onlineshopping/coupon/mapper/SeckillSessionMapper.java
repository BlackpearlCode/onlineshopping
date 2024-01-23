package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.SeckillSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SeckillSessionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillSession record);

    int insertSelective(SeckillSession record);

    SeckillSession selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillSession record);

    int updateByPrimaryKey(SeckillSession record);


    List<SeckillSession> getLatest3DaysSession(@Param("startTime")LocalDateTime startTime,  @Param("endTime") LocalDateTime endTime);
}
package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.HomeSubject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeSubjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HomeSubject record);

    int insertSelective(HomeSubject record);

    HomeSubject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeSubject record);

    int updateByPrimaryKey(HomeSubject record);
}
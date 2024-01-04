package com.onlineshopping.order.mapper;

import com.onlineshopping.order.entity.RefundInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RefundInfo record);

    int insertSelective(RefundInfo record);

    RefundInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RefundInfo record);

    int updateByPrimaryKey(RefundInfo record);
}
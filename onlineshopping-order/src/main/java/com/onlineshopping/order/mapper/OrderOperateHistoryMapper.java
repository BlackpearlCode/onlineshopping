package com.onlineshopping.order.mapper;

import com.onlineshopping.order.entity.OrderOperateHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderOperateHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderOperateHistory record);

    int insertSelective(OrderOperateHistory record);

    OrderOperateHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderOperateHistory record);

    int updateByPrimaryKey(OrderOperateHistory record);
}
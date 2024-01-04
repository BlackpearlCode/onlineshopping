package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.OrderOperateHistory;
public interface OrderOperateHistoryService{

    int deleteByPrimaryKey(Long id);

    int insert(OrderOperateHistory record);

    int insertSelective(OrderOperateHistory record);

    OrderOperateHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderOperateHistory record);

    int updateByPrimaryKey(OrderOperateHistory record);

}

package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.OrderReturnReason;
public interface OrderReturnReasonService{

    int deleteByPrimaryKey(Long id);

    int insert(OrderReturnReason record);

    int insertSelective(OrderReturnReason record);

    OrderReturnReason selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderReturnReason record);

    int updateByPrimaryKey(OrderReturnReason record);

}

package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService{

    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> selectByOrderSn(String orderSn);
}

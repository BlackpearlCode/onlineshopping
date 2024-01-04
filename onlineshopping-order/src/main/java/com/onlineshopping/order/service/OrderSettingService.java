package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.OrderSetting;
public interface OrderSettingService{

    int deleteByPrimaryKey(Long id);

    int insert(OrderSetting record);

    int insertSelective(OrderSetting record);

    OrderSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderSetting record);

    int updateByPrimaryKey(OrderSetting record);

}

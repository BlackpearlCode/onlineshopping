package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.OrderReturnApply;
public interface OrderReturnApplyService{

    int deleteByPrimaryKey(Long id);

    int insert(OrderReturnApply record);

    int insertSelective(OrderReturnApply record);

    OrderReturnApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderReturnApply record);

    int updateByPrimaryKey(OrderReturnApply record);

}

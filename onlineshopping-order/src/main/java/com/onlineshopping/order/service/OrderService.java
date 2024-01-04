package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.Order;
import com.onlineshopping.order.vo.OrderConfirmVo;
import com.onlineshopping.order.vo.OrderSubmitVo;
import com.onlineshopping.order.vo.SubmitOrderResponseVo;

import java.util.concurrent.ExecutionException;

public interface OrderService{

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    // 订单确认页面信息
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    // 提交订单
    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo);
}

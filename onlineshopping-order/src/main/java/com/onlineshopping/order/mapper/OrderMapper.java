package com.onlineshopping.order.mapper;

import com.onlineshopping.order.entity.Order;
import com.onlineshopping.order.entity.OrderItem;
import com.onlineshopping.order.enume.OrderStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    void saveBatch(@Param("orderItems") List<OrderItem> orderItems);

    Order selectByOrderSn(String orderSn);

    List<Order> selectByMemberId(Long id);

    void updateByOrderSn( @Param("orderSn") String orderSn,@Param("status") Byte status);
}
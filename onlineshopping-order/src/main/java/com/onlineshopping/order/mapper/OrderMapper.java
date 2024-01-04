package com.onlineshopping.order.mapper;

import com.onlineshopping.order.entity.Order;
import com.onlineshopping.order.entity.OrderItem;
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
}
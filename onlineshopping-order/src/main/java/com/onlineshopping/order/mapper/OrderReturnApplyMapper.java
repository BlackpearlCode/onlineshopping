package com.onlineshopping.order.mapper;

import com.onlineshopping.order.entity.OrderReturnApply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderReturnApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderReturnApply record);

    int insertSelective(OrderReturnApply record);

    OrderReturnApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderReturnApply record);

    int updateByPrimaryKey(OrderReturnApply record);
}
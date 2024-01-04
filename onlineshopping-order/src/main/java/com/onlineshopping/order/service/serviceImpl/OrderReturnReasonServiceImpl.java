package com.onlineshopping.order.service.serviceImpl;

import com.onlineshopping.order.entity.OrderReturnReason;
import com.onlineshopping.order.mapper.OrderReturnReasonMapper;
import com.onlineshopping.order.service.OrderReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderReturnReasonServiceImpl implements OrderReturnReasonService{

    @Autowired
    private OrderReturnReasonMapper orderReturnReasonMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderReturnReasonMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OrderReturnReason record) {
        return orderReturnReasonMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderReturnReason record) {
        return orderReturnReasonMapper.insertSelective(record);
    }

    @Override
    public OrderReturnReason selectByPrimaryKey(Long id) {
        return orderReturnReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderReturnReason record) {
        return orderReturnReasonMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderReturnReason record) {
        return orderReturnReasonMapper.updateByPrimaryKey(record);
    }

}

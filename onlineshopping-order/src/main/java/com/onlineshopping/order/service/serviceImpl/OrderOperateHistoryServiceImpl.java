package com.onlineshopping.order.service.serviceImpl;

import com.onlineshopping.order.entity.OrderOperateHistory;
import com.onlineshopping.order.mapper.OrderOperateHistoryMapper;
import com.onlineshopping.order.service.OrderOperateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderOperateHistoryServiceImpl implements OrderOperateHistoryService{

    @Autowired
    private OrderOperateHistoryMapper orderOperateHistoryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderOperateHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OrderOperateHistory record) {
        return orderOperateHistoryMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderOperateHistory record) {
        return orderOperateHistoryMapper.insertSelective(record);
    }

    @Override
    public OrderOperateHistory selectByPrimaryKey(Long id) {
        return orderOperateHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderOperateHistory record) {
        return orderOperateHistoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderOperateHistory record) {
        return orderOperateHistoryMapper.updateByPrimaryKey(record);
    }

}

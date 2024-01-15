package com.onlineshopping.ware.service;

import com.onlineshopping.ware.entity.WareOrderTaskDetail;

import java.util.List;

public interface WareOrderTaskDetailService{


    int deleteByPrimaryKey(Long id);

    int insert(WareOrderTaskDetail record);

    int insertSelective(WareOrderTaskDetail record);

    WareOrderTaskDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareOrderTaskDetail record);

    int updateByPrimaryKey(WareOrderTaskDetail record);

    //保存库存订单详情
    void save(WareOrderTaskDetail wareOrderTaskDetail);

    //根据任务id获取订单详情
    List<WareOrderTaskDetail> getOrderTaskDetailByTaskId(Long id);
}

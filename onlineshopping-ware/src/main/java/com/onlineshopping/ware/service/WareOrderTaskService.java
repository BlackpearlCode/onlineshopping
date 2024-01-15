package com.onlineshopping.ware.service;

import com.onlineshopping.ware.entity.WareOrderTask;
public interface WareOrderTaskService{


    int deleteByPrimaryKey(Long id);

    int insert(WareOrderTask record);

    int insertSelective(WareOrderTask record);

    WareOrderTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareOrderTask record);

    int updateByPrimaryKey(WareOrderTask record);

    // 保存库存工作单
    void save(WareOrderTask wareOrderTask);

    // 查询库存工作单
    WareOrderTask getOrderTaskByOrderSn(String orderSn);
}

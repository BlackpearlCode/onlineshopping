package com.onlineshopping.ware.service;

import com.onlineshopping.ware.entity.WareOrderTaskDetail;
public interface WareOrderTaskDetailService{


    int deleteByPrimaryKey(Long id);

    int insert(WareOrderTaskDetail record);

    int insertSelective(WareOrderTaskDetail record);

    WareOrderTaskDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareOrderTaskDetail record);

    int updateByPrimaryKey(WareOrderTaskDetail record);

}

package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.RefundInfo;
public interface RefundInfoService{

    int deleteByPrimaryKey(Long id);

    int insert(RefundInfo record);

    int insertSelective(RefundInfo record);

    RefundInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RefundInfo record);

    int updateByPrimaryKey(RefundInfo record);

}

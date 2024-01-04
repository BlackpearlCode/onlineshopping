package com.onlineshopping.ware.service;

import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.ware.entity.PurchaseDetail;

import java.util.List;
import java.util.Map;

public interface PurchaseDetailService{


    int deleteByPrimaryKey(Long id);

    int insert(PurchaseDetail record);

    int insertSelective(PurchaseDetail record);

    PurchaseDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseDetail record);

    int updateByPrimaryKey(PurchaseDetail record);

    PageEntity getAll(Map<String, Object> params);

    void batchDelete(List<Long> ids);
}

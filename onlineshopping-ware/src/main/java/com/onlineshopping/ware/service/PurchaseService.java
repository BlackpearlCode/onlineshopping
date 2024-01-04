package com.onlineshopping.ware.service;

import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.ware.entity.Purchase;
import com.onlineshopping.ware.vo.MergeVo;
import com.onlineshopping.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

public interface PurchaseService{


    int deleteByPrimaryKey(Long id);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

    PageEntity getAll(Map<String, Object> params);

    List<Purchase> getUnreceiveList();

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);

    void batchDel(List<Long> ids);
}

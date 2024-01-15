package com.onlineshopping.ware.service;

import com.onlineshopping.common.to.mq.OrderTo;
import com.onlineshopping.common.to.mq.StockLockedTo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.ware.entity.WareSku;
import com.onlineshopping.ware.vo.SkusHasStockVo;
import com.onlineshopping.ware.vo.WareSkuLockVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WareSkuService{


    int deleteByPrimaryKey(Long id);

    int insert(WareSku record);

    int insertSelective(WareSku record);

    WareSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareSku record);

    int updateByPrimaryKey(WareSku record);

    PageEntity getAll(Map<String, Object> params);

    // 查询sku是否有库存
    List<SkusHasStockVo> getSkusHasStock(List<Long> skuIds);
    // 锁定库存
    Boolean orderLockStock(WareSkuLockVo vo);
    // 解锁库存
    void unlockStock(StockLockedTo stockLockedTo);

    void unlockStock(OrderTo order);
}

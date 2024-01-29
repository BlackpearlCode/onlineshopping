package com.onlineshopping.seckill.service;

import com.onlineshopping.seckill.to.SeckillSkuRedisTo;

import java.util.List;

public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    // 获取当前时间可以参与秒杀的商品
    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    // 获取商品的秒杀信息
    SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);
}

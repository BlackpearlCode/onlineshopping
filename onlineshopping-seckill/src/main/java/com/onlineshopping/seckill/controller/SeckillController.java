package com.onlineshopping.seckill.controller;


import com.onlineshopping.common.utils.Result;
import com.onlineshopping.seckill.service.SeckillService;
import com.onlineshopping.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 返回当前时间可以参与的秒杀商品信息
     * @return
     */
    @GetMapping("/getCurrentSeckillSkus")
    public Result getCurrentSeckillSkus(){
        List<SeckillSkuRedisTo> vos=seckillService.getCurrentSeckillSkus();
        return Result.ok().setData(vos);
    }

    @GetMapping("/sku/seckill/{skuId}")
    public Result getSkuSeckillInfo(@PathVariable("skuId") Long skuId){
        SeckillSkuRedisTo to=seckillService.getSkuSeckillInfo(skuId);
        return Result.ok().setData(to);
    }
}

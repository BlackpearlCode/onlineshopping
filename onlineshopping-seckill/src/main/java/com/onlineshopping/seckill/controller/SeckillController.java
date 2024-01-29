package com.onlineshopping.seckill.controller;


import com.onlineshopping.common.utils.Result;
import com.onlineshopping.seckill.service.SeckillService;
import com.onlineshopping.seckill.to.SeckillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @GetMapping("/getCurrentSeckillSkus")
    public Result getCurrentSeckillSkus(){
        List<SeckillSkuRedisTo> vos=seckillService.getCurrentSeckillSkus();
        return Result.ok().setData(vos);
    }
}

package com.onlineshopping.seckill.scheduled;

import com.onlineshopping.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 秒杀商品的定时上架
 */
@Slf4j
@Service
@EnableScheduling
public class SeckillSkuScheduled {

    @Autowired
    private SeckillService seckillService;
    @Scheduled(cron = "0 * * * * ?")
    public void uploadSeckillSkuLatest3Days(){
        log.info("定时上架秒杀商品任务启动。。。。。");
        seckillService.uploadSeckillSkuLatest3Days();
    }
}

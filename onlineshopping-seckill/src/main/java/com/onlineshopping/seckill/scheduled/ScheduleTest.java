package com.onlineshopping.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * 定时任务：
 *      1.@EnableScheduling开启定时任务功能
 *      2.@Scheduled开启一个定时任务
 *
 * 异步任务：
 *      1：@EnableAsync开启异步任务功能
 *      2：@Async给希望异步执行的方法上标注注解
 */
//@EnableScheduling
//@Component
@Slf4j
//@EnableAsync
public class ScheduleTest {
    /**
     * 1.spring中6位组成，不允许第七位的年
     */
//    @Async
//    @Scheduled(cron = "* * * * * ?")
    public void hello() throws InterruptedException {
        log.info("hello.....");
        Thread.sleep(3000);
    }
}

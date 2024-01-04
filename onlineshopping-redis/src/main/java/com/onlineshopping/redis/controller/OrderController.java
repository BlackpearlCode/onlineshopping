package com.onlineshopping.redis.controller;

import com.onlineshopping.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order/redis")
public class OrderController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/saveToken")
    public void saveToken(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("time") long time){
        redisUtil.set(key,value,time);
    }

    @RequestMapping("/getValue")
    public String getValue(@RequestParam("key") String key){
        return redisUtil.get(key).toString();
    };

    /**
     * 执行lua脚本
     *
     * @param script
     * @param keys
     * @param value
     * @return
     */
    @RequestMapping("/executeLua")
    public Long executeLua(@RequestParam("script") String script, @RequestParam("keys") List<String> keys, @RequestParam("value") String value){
        return redisUtil.executeRedisLock( script, keys, value);
    }
}



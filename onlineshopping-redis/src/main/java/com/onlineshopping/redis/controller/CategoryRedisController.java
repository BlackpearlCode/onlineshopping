package com.onlineshopping.redis.controller;

import com.onlineshopping.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RequestMapping("/redis")
@RestController
public class CategoryRedisController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取hashKey对应的所有键值
     * @param key
     * @return
     * @param <T>
     */
    @RequestMapping("/getCacheHit")
    public<T> Map<String, T> getCacheHit(@RequestParam("key") String key){
        return redisUtil.hmget(key);
    }

    /**
     *存放set集合
     * @param key:键
     * @param map:对应多个键值
     * @param time:时间(秒)
     * @param <T>
     */
    @RequestMapping("/saveSet")
    public<T> void hmset(@RequestParam("key") String key, @RequestBody Map<String, T> map, @RequestParam("time") long time){
        redisUtil.hmset(key,map,time);
    }
}

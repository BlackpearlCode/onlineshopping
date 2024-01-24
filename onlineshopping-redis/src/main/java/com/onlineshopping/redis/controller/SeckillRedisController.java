package com.onlineshopping.redis.controller;

import com.onlineshopping.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SeckillRedisController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/saveList")
    public void saveList(@RequestParam("key") String key,@RequestParam("value") Object value){
        redisUtil.lSet(key, value);
    }

    @RequestMapping("/saveMap")
    public void saveMap(@RequestParam("key") String key, @RequestBody Map<String, Object> map){
        redisUtil.hmset(key, map);
    }
}

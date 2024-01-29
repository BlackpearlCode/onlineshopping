package com.onlineshopping.redis.controller;

import com.onlineshopping.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @RequestMapping("/KeyIsExist")
    public Boolean KeyIsExist(@RequestParam("key") String key){
        return redisUtil.hasKey(key);
    }

    @RequestMapping("/sGet")
    public Set<String> sGet(@RequestParam("key") String key){
        return redisUtil.fuzzyQueryByKey(key);
    }

    @RequestMapping("/getValue")
    public List<String> getValue(@RequestParam("key")String key ){
        return redisUtil.lGet(key,0,-1);
    }

    @RequestMapping("/getMap")
    public<T> Map<String, T> getMap(@RequestParam("key")String key){
        return redisUtil.hmget(key);
    }
}

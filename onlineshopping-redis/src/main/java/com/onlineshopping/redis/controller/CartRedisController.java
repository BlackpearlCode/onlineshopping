package com.onlineshopping.redis.controller;

import com.onlineshopping.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cart/redis")
public class CartRedisController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/saveHash")
    public boolean saveHash(@RequestParam("key") String key, @RequestParam("item") String item, @RequestParam("value") Object value){
        return redisUtil.hset(key,item,value);
    }


    @RequestMapping("/getHash")
    public String getHash(@RequestParam("key") String key, @RequestParam("item") String item){
        boolean bool = redisUtil.hHasKey(key, item);
        if(!bool){
            return null;
        }
        Object object = redisUtil.hget(key, item);
        return object.toString();
    }

    @RequestMapping("/getAllHash")
    public Map<String, String> getAllHash(@RequestParam("key") String key){
       return redisUtil.hmget(key);
    }

    @RequestMapping("/delHash")
    public void delHash(@RequestParam("key") String key){
        redisUtil.del(key);
    }

    @RequestMapping("/delHashKey")
    public void delHashKey(@RequestParam("key") String key,@RequestParam("item") String item){
        redisUtil.hdel(key,item);
    }
}

package com.onlineshopping.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "onlineshopping-redis")
public interface RedisFeginService {

    @RequestMapping("/registy/redis/saveCode")
    void saveCode(@RequestParam("key") String key,@RequestParam("code")String code,@RequestParam("time") long time);

    @RequestMapping("/registy/redis/getCode")
    String getCode(@RequestParam("key") String key);

    @RequestMapping("/registy/redis/delCode")
    void delCode(@RequestParam("key") String key);
}

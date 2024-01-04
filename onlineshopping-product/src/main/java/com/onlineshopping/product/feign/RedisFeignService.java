package com.onlineshopping.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "onlineshopping-redis")
public interface RedisFeignService {

    @RequestMapping("redis/getCacheHit")
    public<T> Map<String, T> getCacheHit(@RequestParam("key") String key);

    @RequestMapping("redis/saveSet")
    public<T> void hmset(@RequestParam("key") String key, @RequestBody Map<String, T> map, @RequestParam("time") long time);
}

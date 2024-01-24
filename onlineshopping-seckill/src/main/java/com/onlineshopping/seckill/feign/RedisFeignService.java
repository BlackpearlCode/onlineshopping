package com.onlineshopping.seckill.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("onlineshopping-redis")
public interface RedisFeignService {
    @RequestMapping("/saveList")
    public void saveList(@RequestParam("key") String key,@RequestParam("value") Object value);

    @RequestMapping("/saveMap")
    public void saveMap(@RequestParam("key") String key, @RequestBody Map<String, Object> map);
}


package com.onlineshopping.seckill.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("onlineshopping-redis")
public interface RedisFeignService {
    @RequestMapping("/saveList")
    public void saveList(String key, Object value);

    @RequestMapping("/saveMap")
    public void saveMap(String key, Map<String, Object> map);
}


package com.onlineshopping.seckill.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient("onlineshopping-redis")
public interface RedisFeignService {
    @RequestMapping("/saveList")
    public void saveList(@RequestParam("key") String key,@RequestParam("value") Object value);

    @RequestMapping("/saveMap")
    public void saveMap(@RequestParam("key") String key, @RequestBody Map<String, Object> map);

    @RequestMapping("/KeyIsExist")
    public Boolean KeyIsExist(@RequestParam("key") String key);

    @RequestMapping("/sGet")
    public Set<String> sGet(@RequestParam("key") String key);

    @RequestMapping("/getValue")
    public List<String> getValue(@RequestParam("key") String key );

    @RequestMapping("/getMap")
    public<T> Map<String, T> getMap(@RequestParam("key") String key);
}


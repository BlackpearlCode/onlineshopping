package com.onlineshopping.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("onlineshopping-redis")
public interface RedisFeignService {

    @RequestMapping("/order/redis/saveToken")
    public void saveToken(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("time") long time);

    @RequestMapping("/order/redis/getValue")
    public String getValue(@RequestParam("key") String key);

    @RequestMapping("/order/redis/executeLua")
    public Long executeLua(@RequestParam("script") String script, @RequestParam("keys") List<String> keys, @RequestParam("value") String value);
}

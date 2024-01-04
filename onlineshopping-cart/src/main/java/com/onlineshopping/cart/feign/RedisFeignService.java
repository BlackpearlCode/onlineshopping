package com.onlineshopping.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("onlineshopping-redis")
public interface RedisFeignService {

    @RequestMapping("/cart/redis/saveHash")
    public boolean saveHash(@RequestParam("key") String key, @RequestParam("item") String item, @RequestParam("value") Object value);

    @RequestMapping("/cart/redis/getHash")
    public String getHash(@RequestParam("key") String key, @RequestParam("item") String item);

    @RequestMapping("/cart/redis/getAllHash")
    public Map<String, String> getAllHash(@RequestParam("key") String key);

    @RequestMapping("/cart/redis/delHash")
    public void delHash(@RequestParam("key") String key);

    @RequestMapping("/cart/redis/delHashKey")
    public void delHashKey(@RequestParam("key") String key,@RequestParam("item") String item);
}

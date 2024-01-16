package com.onlineshopping.member.feign;

import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "onlineshopping-order")
public interface OrderFeignService {

    @PostMapping("/listWithItem")
    public Result listWithItem(@RequestBody Map<String,Object> params);
}

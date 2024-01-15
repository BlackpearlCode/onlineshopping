package com.onlineshopping.ware.feign;

import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("onlineshopping-order")
public interface OrderFeignService {

    @GetMapping("/status/{orderSn}")
    public Result getOrderStatus(@PathVariable("orderSn") String orderSn);

}

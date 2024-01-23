package com.onlineshopping.seckill.feign;

import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("onlineshopping-coupon")
public interface CouponFeignService {

    @GetMapping("/coupon/seckillSession/atest3DaysSession")
    public Result getLatest3DaysSession();
}

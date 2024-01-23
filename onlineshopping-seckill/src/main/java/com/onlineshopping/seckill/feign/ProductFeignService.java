package com.onlineshopping.seckill.feign;

import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("onlineshopping-product")
public interface ProductFeignService {
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public Result info(@PathVariable("skuId") Long skuId);
}

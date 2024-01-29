package com.onlineshopping.product.feign;

import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient("onlineshopping-seckill")
public interface SeckillFeignService {
    @GetMapping("/sku/seckill/{skuId}")
    public Result getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}

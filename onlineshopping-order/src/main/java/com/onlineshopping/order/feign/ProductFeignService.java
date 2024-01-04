package com.onlineshopping.order.feign;

import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("onlineshopping-product")
public interface ProductFeignService {

    @GetMapping("/product/spuinfo/skuId/{id}")
    public Result getSpuInfoBySkuId(@PathVariable("id") Long id);
}

package com.onlineshopping.cart.feign;


import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("onlineshopping-product")
public interface ProductFeignService {
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public Result info(@PathVariable("skuId") Long skuId);

    @GetMapping("/product/skuinfo/stringlist/{skuId}")
    public List<String> getSkuSaleAtttrValues(@PathVariable ("skuId") Long skuId);

    @RequestMapping("/product/skuinfo/{skuId}/price")
    public BigDecimal getPrice(@PathVariable("skuId") Long skuId);
}

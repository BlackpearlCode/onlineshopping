package com.onlineshopping.product.feign;

import com.onlineshopping.common.to.SkuReductionTo;
import com.onlineshopping.common.to.SpuBoundTo;
import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("onlineshopping-coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/skufullreduction/saveInfo")
    Result saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);

    @RequestMapping("/coupon/spubounds/save")
    Result saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);
}

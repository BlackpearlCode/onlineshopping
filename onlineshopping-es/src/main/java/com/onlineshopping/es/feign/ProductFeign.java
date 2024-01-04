package com.onlineshopping.es.feign;

import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("onlineshopping-product")
public interface ProductFeign {
    @RequestMapping("product/attr/info/{attrId}")
    Result info(@PathVariable("attrId") Long attrId);


    @RequestMapping("product/brand/infos")
    Result brandInfoByIds(@RequestParam List<Long> brandIds);
}

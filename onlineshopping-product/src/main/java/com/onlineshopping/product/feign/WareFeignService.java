package com.onlineshopping.product.feign;



import com.onlineshopping.common.utils.Result;
import com.onlineshopping.product.vo.SkusHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("onlineshopping-ware")
public interface WareFeignService {

    @RequestMapping("ware/waresku/hasStock")
    Result getSkusHasStock(@RequestBody List<Long> skuIds);
}

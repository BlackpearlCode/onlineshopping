package com.onlineshopping.order.feign;

import com.onlineshopping.order.vo.WareSkuLockVo;
import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("onlineshopping-ware")
public interface WmsFeignService {
    @GetMapping("ware/waresku/hasStock")
    public Result getSkusHasStock(@RequestBody List<Long> skuIds);

    @GetMapping("ware/wareinfo/fare")
    public Result fare(@RequestParam("addrId") Long id);

    @PostMapping("ware/waresku/lock/order")
    public Result orderLockStock(@RequestBody WareSkuLockVo vo);
}

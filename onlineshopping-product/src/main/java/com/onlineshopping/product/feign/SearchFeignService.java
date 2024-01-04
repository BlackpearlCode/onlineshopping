package com.onlineshopping.product.feign;

import com.onlineshopping.common.es.SkuEsModel;
import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("onlineshopping-elasticsearch")
public interface SearchFeignService {

    @RequestMapping("/search/save/product")
    public Result productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}

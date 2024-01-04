package com.onlineshopping.coupon.controller;


import com.onlineshopping.common.to.SkuReductionTo;
import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.coupon.service.serviceImpl.SkuFullReductionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coupon/skufullreduction")
public class SkuReductionController {

    @Autowired
    private SkuFullReductionServiceImpl fullReductionService;

    /**
     * 满减
     * @param skuReductionTo
     * @return
     */
    @RequestMapping("/saveInfo")
    public Result saveInfo(@RequestBody SkuReductionTo skuReductionTo){
        fullReductionService.save(skuReductionTo);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }
}

package com.onlineshopping.coupon.controller;



import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.coupon.entity.SpuBounds;
import com.onlineshopping.coupon.service.serviceImpl.SpuBoundsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("coupon/spubounds")
@RestController
public class SpuBoundsController {

    @Autowired
    private SpuBoundsServiceImpl spuBoundsService;

    @RequestMapping("/save")
    public Result save(@RequestBody SpuBounds SpuBounds){
        spuBoundsService.insert(SpuBounds);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }
}

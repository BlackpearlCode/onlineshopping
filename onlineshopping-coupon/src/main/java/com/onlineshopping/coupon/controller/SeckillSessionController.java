package com.onlineshopping.coupon.controller;

import com.onlineshopping.common.utils.Result;
import com.onlineshopping.coupon.entity.SeckillSession;
import com.onlineshopping.coupon.service.SeckillSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("coupon/seckillSession")
public class SeckillSessionController {

    @Autowired
    private SeckillSessionService seckillSessionService;

    @GetMapping("/atest3DaysSession")
    public Result getLatest3DaysSession(){
        List<SeckillSession> sessionList=seckillSessionService.getLatest3DaysSession();
        return Result.ok().setData(sessionList);
    }

}

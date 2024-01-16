package com.onlineshopping.order.controller;

import com.alipay.api.AlipayApiException;
import com.onlineshopping.order.config.AlipayTemplate;
import com.onlineshopping.order.service.OrderService;
import com.onlineshopping.order.vo.PayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class PayController {

    @Autowired
    private AlipayTemplate alipayTemplate;
    @Autowired
    private OrderService orderService;
    @ResponseBody
    @GetMapping(value = "/aliPayOrder",produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVo payVo=orderService.getOrderPay(orderSn);
        //返回一个页面，将这个页面交给浏览器
        String pay = alipayTemplate.pay(payVo);
        log.info("pay:{}", pay);
        return pay;
    }
}

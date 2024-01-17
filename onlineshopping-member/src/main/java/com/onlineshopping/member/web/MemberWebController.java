package com.onlineshopping.member.web;

import com.onlineshopping.common.utils.Result;
import com.onlineshopping.member.feign.OrderFeignService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class MemberWebController {

    @Autowired
    private OrderFeignService orderFeignService;

    @RequestMapping("/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "page",defaultValue = "1") Integer pageNum, Model model, HttpServletRequest request){
        //获取支付宝给我们传来的所有请求数据

        //查出当前登录的用户的所有订单列表数据
        HashMap<String, Object> page = new HashMap<>();
        page.put("page",pageNum);
        Result result = orderFeignService.listWithItem(page);
        model.addAttribute("orders",result);
        return "orderList";
    }
}

package com.onlineshopping.order.controller;

import com.onlineshopping.order.entity.OrderReturnReason;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RabbitController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg")
    public String sendMsg(){
        OrderReturnReason orderReturnReason = new OrderReturnReason();
        orderReturnReason.setId(1L);
        orderReturnReason.setCreateTime(new Date());
        orderReturnReason.setName("哈哈哈");
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",orderReturnReason);
        return "ok";
    }
}

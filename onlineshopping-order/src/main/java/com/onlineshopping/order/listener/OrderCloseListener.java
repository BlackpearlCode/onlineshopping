package com.onlineshopping.order.listener;

import com.onlineshopping.order.entity.Order;
import com.onlineshopping.order.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Slf4j
@Service
@RabbitListener(queues = "order.release.order.queue",ackMode = "MANUAL")
public class OrderCloseListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void listener(Order order, Message message, Channel channel) throws IOException {
      log.info("收到过期订单，准备关闭订单：{}",order.getOrderSn());
      try{
          orderService.closeOrder(order);
          channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
      }catch (Exception e){
          channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
      }
    }
}

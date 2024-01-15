package com.onlineshopping.ware.listener;


import com.onlineshopping.common.to.mq.OrderTo;
import com.onlineshopping.common.to.mq.StockLockedTo;
import com.onlineshopping.ware.service.WareSkuService;
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
@RabbitListener(queues = "stock.release.stock.queue",ackMode = "MANUAL")
public class StockReleaseListener {


    @Autowired
    private WareSkuService wareSkuService;

    /**
     *库存自动解锁
     *  下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚。之前锁定的库存就要自动解锁
     */

    @RabbitHandler
    public void handleStockedRelease(StockLockedTo stockLockedTo, Message message, Channel channel) throws IOException {

        try{
            log.info("收到解锁库存的消息");
           wareSkuService.unlockStock(stockLockedTo);
           channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            //解锁失败将消息重新放入队列，让别人消费
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }

    @RabbitHandler
    public void handleOrderCloseRelease(OrderTo order, Message message, Channel channel) throws IOException {
        log.info("订单关闭准备解锁库存......");
        try{
            wareSkuService.unlockStock(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            //解锁失败将消息重新放入队列，让别人消费
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }


}

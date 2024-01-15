package com.onlineshopping.ware.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Slf4j
@Configuration
public class MyRabbitConfig {

    /**
     * 使用json序列化，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    /**
     * 创建一个交换机
     * @return
     */
    @Bean
    public Exchange stockEventExchange(){
        /**
         * name: 交换机名称
         * durable: 是否持久化;true: 持久化;false: 不持久化
         * autoDelete: 是否自动删除;true: 自动删除;false: 不自动删除
         */
        TopicExchange topicExchange = new TopicExchange("stock-event-exchange", true, false);
        return topicExchange;
    }

    @Bean
    public Queue stockReleaseStockQueue(){
        /**
         * name: 队列名称
         * durable: 是否持久化;true: 持久化;false: 不持久化
         * exclusive: 是否独占;true: 独占;false: 共享
         * autoDelete: 是否自动删除;true: 自动删除;false: 不自动删除
         */
        Queue queue = new Queue("stock.release.stock.queue", true, false, false);

        return queue;
    }
    @Bean
    public Queue stockDelayQueue(){
        HashMap<String, Object> arguments = new HashMap<>();
        /**
         * x-dead-letter-exchange：死信交换机
         * x-dead-letter-routing-key：死信路由键
         * x-message-ttl：消息的过期时间，单位毫秒
         */
        arguments.put("x-dead-letter-exchange", "stock-event-exchange");
        arguments.put("x-dead-letter-routing-key","stock.release");
        arguments.put("x-message-ttl",120000);
        /**
         * name: 队列名称
         * durable: 是否持久化;true: 持久化;false: 不持久化
         * exclusive: 是否独占;true: 独占;false: 共享
         * autoDelete: 是否自动删除;true: 自动删除;false: 不自动删除
         * arguments: 队列的参数
         */
        Queue queue = new Queue("stock.delay.queue", true, false, false, arguments);
        return queue;
    }

    /**
     * destination: 绑定队列名称
     * type: 绑定类型
     *exchange: 交换机
     * routingKey: 路由键
     * arguments: 绑定的参数
     * @return
     */
    @Bean
    public Binding stockReleaseBinding(){
        Binding binding = new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.release.#", null);
        return binding;
    }

    @Bean
    public Binding stockLockedBinding(){
        Binding binding = new Binding("stock.delay.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.locked", null);
        return binding;
    }



}
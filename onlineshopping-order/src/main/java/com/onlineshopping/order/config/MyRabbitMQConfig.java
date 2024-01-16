package com.onlineshopping.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MyRabbitMQConfig {

    /**
     * 创建一个队列
     * @return
     */
    @Bean
    public Queue orderDeliveryQueue(){
        /**
         * name: 队列名称
         * durable: 是否持久化;true: 持久化;false: 不持久化
         * exclusive: 是否独占;true: 独占;false: 共享
         * autoDelete: 是否自动删除;true: 自动删除;false: 不自动删除
         * arguments: 队列的参数
         */
        HashMap<String, Object> arguments = new HashMap<>();
        /**
         * x-dead-letter-exchange：死信交换机
         * x-dead-letter-routing-key：死信路由键
         * x-message-ttl：消息的过期时间，单位毫秒
         */
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        arguments.put("x-dead-letter-routing-key","order.release.order");
        arguments.put("x-message-ttl",120000);
        Queue queue = new Queue("order.delay.queue", true, false, false, arguments);

        return queue;
    }

    /**
     * 创建一个队列
     * @return
     */
    @Bean
    public Queue orderReleaseOrderQueue(){
        /**
         * name: 队列名称
         * durable: 是否持久化;true: 持久化;false: 不持久化
         * exclusive: 是否独占;true: 独占;false: 共享
         * autoDelete: 是否自动删除;true: 自动删除;false: 不自动删除
         */
        Queue queue = new Queue("order.release.order.queue", true, false, false);

        return queue;
    }

    /**
     * 创建一个交换机
     * @return
     */
    @Bean
    public Exchange orderEventExchange(){
        /**
         * name: 交换机名称
         * durable: 是否持久化;true: 持久化;false: 不持久化
         * autoDelete: 是否自动删除;true: 自动删除;false: 不自动删除
         */
        TopicExchange topicExchange = new TopicExchange("order-event-exchange", true, false);
        return topicExchange;
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
    public Binding orderCreateOrderBinding(){
        Binding binding = new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order", null);
        return binding;
    }
    @Bean
    public Binding orderReleaseOrderBinding(){
        Binding binding = new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order", null);
        return binding;
    }

    /**
     *订单释放直接和库存进行绑定
     *
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding(){
        Binding binding = new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.other.#", null);
        return binding;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate();
        configurer.configure(template, connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}

package com.onlineshopping.order.config;

import com.alibaba.fastjson.JSON;
import com.onlineshopping.order.entity.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.HashMap;


@Slf4j
@Configuration
public class MyRabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 1.设置确认回调： ConfirmCallback
     * 先在配置文件中开启 publisher-confirms: true
     * @PostConstruct: MyRabbitConfig对象创建完成以后 执行这个方法
     *
     *  2.消息抵达队列的确认回调
     * 　	开启发送端消息抵达队列确认
     *     publisher-returns: true
     *     	只要抵达队列，以异步优先回调我们这个 returnconfirm
     *     template:
     *       mandatory: true
     *	3.消费端确认(保证每一个消息被正确消费才可以broker删除消息)
     *		1.默认是自动确认的 只要消息接收到 服务端就会移除这个消息
     *
     *		如何签收:
     *			签收: channel.basicAck(deliveryTag, false); 业务成功
     *			拒签: channel.basicNack(deliveryTag, false,true); 业务失败
     *	配置文件中一定要加上这个配置
     *		listener:
     *       simple:
     *         acknowledge-mode: manual
     */
    public void initRabbitTemplate(){
        /**
         * 		设置确认回调
         *  correlationData: 消息的唯一id
         *  ack： 消息是否成功收到
         * 	cause：失败的原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack , cause) -> log.info("\n收到消息: " + correlationData + "\tack: " + ack + "\tcause： " + cause));

        /**
         * 设置消息抵达队列回调：可以很明确的知道那些消息失败了
         *
         * message: 投递失败的消息详细信息
         * replyCode: 回复的状态码
         * replyText: 回复的文本内容
         * exchange: 当时这个发送给那个交换机
         * routerKey: 当时这个消息用那个路由键
         */
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.error("Fail Message [" + returnedMessage.getMessage() + "]" + "\treplyCode: " + returnedMessage.getReplyCode() + "\treplyText:" + returnedMessage.getReplyText() + "\texchange:" + returnedMessage.getExchange() + "\trouterKey:" + returnedMessage.getRoutingKey());
            }
        });

    }

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
        arguments.put("x-message-ttl",60000);
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
    public Binding orderCreateOrderBing(){
        Binding binding = new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order", null);
        return binding;
    }
    @Bean
    public Binding orderReleaseOrderBing(){
        Binding binding = new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order", null);
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




    @RabbitListener(queues = "order.release.order.queue",ackMode = "MANUAL")
    public void listener(@Payload Order order, Channel channel, Message message) throws IOException {
        log.info("收到过期的订单消息，准备打印订单:,{}",order.getOrderSn());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }




}
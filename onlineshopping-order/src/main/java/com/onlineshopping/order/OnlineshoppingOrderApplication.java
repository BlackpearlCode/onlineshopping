package com.onlineshopping.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRabbit
@MapperScan("com.onlineshopping.order.mapper")
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableFeignClients
public class OnlineshoppingOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineshoppingOrderApplication.class, args);
    }

}

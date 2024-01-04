package com.onlineshopping.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OnlineshoppingRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineshoppingRedisApplication.class, args);
    }

}

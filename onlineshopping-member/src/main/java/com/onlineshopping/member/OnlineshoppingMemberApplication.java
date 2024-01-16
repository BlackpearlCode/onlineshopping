package com.onlineshopping.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages={"com.onlineshopping.member.mapper"})
public class OnlineshoppingMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineshoppingMemberApplication.class, args);
    }

}

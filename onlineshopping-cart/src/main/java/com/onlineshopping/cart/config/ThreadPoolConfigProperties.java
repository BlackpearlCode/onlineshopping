package com.onlineshopping.cart.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
public class ThreadPoolConfigProperties {

    //核心线程数
    @Value("${gulimall.pool.core-pool-size}")
    private Integer corePoolSize;
    @Value("${gulimall.pool.max-pool-size}")
    //最大线程数
    private Integer maxPoolSize;
    @Value("${gulimall.pool.keep-alive-time}")
    //线程空闲时间
    private Integer keepAliveTime;

}

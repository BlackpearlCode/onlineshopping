package com.onlineshopping.product.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Data
public class ThreadPoolConfigProperties {

    //核心线程数
    @Value("${gulimall.pool.core-pool-size}")
    private Integer corePoolSize;
    //最大线程数
    @Value("${gulimall.pool.max-pool-size}")
    private Integer maxPoolSize;
    //线程空闲时间
    @Value("${gulimall.pool.keep-alive-time}")
    private Integer keepAliveTime;

}

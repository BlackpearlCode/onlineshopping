package com.onlineshopping.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ThreadPoolConfigProperties {

    //核心线程数
    @Value("${onlineshopping.pool.core-pool-size}")
    private Integer corePoolSize;
    //最大线程数
    @Value("${onlineshopping.pool.max-pool-size}")
    private Integer maxPoolSize;
    //线程空闲时间
    @Value("${onlineshopping.pool.keep-alive-time}")
    private Integer keepAliveTime;

}

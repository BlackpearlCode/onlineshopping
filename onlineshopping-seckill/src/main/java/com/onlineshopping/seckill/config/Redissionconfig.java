package com.onlineshopping.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Redissionconfig {

    /**
     * destroyMethod = "shutdown":销毁方法，服务停止后会调用这个方法进行销毁
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        //创建配置
        Config config = new Config();
        config.useSingleServer()
                .setPassword("123456")
                .setAddress("redis://47.99.142.201:6379");
        //根据config创建RedissonClient实例
        return Redisson.create(config);
    }
}

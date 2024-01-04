package com.onlineshopping.product.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//描述注解的使用范围
@Retention(value = RetentionPolicy.RUNTIME)//表示注解的生命周期；RUNTIME:运行时；CLASS：存在到字节码文件中；SOURCE:只存在内文件中，在class字节码不存在
public @interface GmallCache {
    //缓存的前缀
    String prefix() default "cache:";
    //缓存的后缀
    String suffix() default ":info";
}

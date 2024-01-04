package com.onlineshopping.product.aop;

import com.onlineshopping.product.feign.RedisFeignService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
@Aspect
public class GmallCacheAspect {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisFeignService redisUtil;



    /**
     * 使用sop实现分布式锁和缓存
     * Arround:环绕通知
     *  value:切入位置
     *  1.定义获取数据的key
     *    a.获取添加了@GmallCache注解的方法（获取注解的属性、方法的参数）
     *    b.获取数据
     */

    @Before("@annotation(com.onlineshopping.product.aop.GmallCache)")
    public void before(){
        System.out.println("进入AOP.......");
    }

    @Around("@annotation(com.onlineshopping.product.aop.GmallCache)")
    public<T> Map<String,T> cacheGmallAspect(ProceedingJoinPoint joinPoint){

        //获取添加了注解的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取注解
        GmallCache gmallCache = signature.getMethod().getAnnotation(GmallCache.class);
        //获取属性前缀
        String prefix = gmallCache.prefix();
        //获取方法传入的参数
        Object[] args = joinPoint.getArgs();
        //组合获取数据的key
        String key = prefix;
        //从缓存中获取数据
        Map<String,T> map=cacheHit(key);
        try {
            //判断
            if(!CollectionUtils.isEmpty(map)){
                System.out.println("从缓存中查数据......");
                //缓存中数据直接返回缓存
                return map;
            }
            //缓存中没有数据,需要从数据库中查询
            //定义锁的key
            String lockKey = prefix + "-lock";
            //上锁
            RLock lock = redissonClient.getLock(lockKey);
            //尝试获取锁，成功获取锁返回true，获取锁失败返回false
            boolean flag = lock.tryLock();
            if(!flag){
                //睡眠
                Thread.sleep(100);
                //自旋
                return cacheGmallAspect(joinPoint);
            }
            //获取锁成功
            //查询数据库，执行切入的方法体实际上就是查询数据库
            try{
                Object object=joinPoint.proceed(args);
                //将查询到的数据存储到缓存中
                Thread.sleep(10000);
                map= (Map<String, T>) object;
            }finally {
                //释放锁
                lock.unlock();
            }
            return map;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
    
    //从缓存中获取数据
    private<T> Map<String, T> cacheHit(String key){
        //获取数据
        Map<String, T> map= redisUtil.getCacheHit(key);
        if(map.isEmpty()){
            return null;
        }
       return map;
    }

}

package com.onlineshopping.seckill.service.impl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.seckill.feign.CouponFeignService;
import com.onlineshopping.seckill.feign.ProductFeignService;
import com.onlineshopping.seckill.feign.RedisFeignService;
import com.onlineshopping.seckill.service.SeckillService;
import com.onlineshopping.seckill.to.SeckillSkuRedisTo;
import com.onlineshopping.seckill.vo.SeckillSessionsWithSkus;
import com.onlineshopping.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private RedisFeignService redisFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    private final String SESSION_CACHE_PREFIX = "seckill:session:";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus";
    private final String SKU_STOCK_SEMAPHORE="seckill:stock:";

    @Override
    public void uploadSeckillSkuLatest3Days() {
        //扫描最近三天需要参与秒杀的活动
        Result session = couponFeignService.getLatest3DaysSession();
        if(session.getCode() == 0){
            Object object = session.get("data");

            List<SeckillSessionsWithSkus> sessionData = JSON.parseObject(JSON.toJSONString(object), new TypeReference<>() {});
            //将活动信息和商品信息上传到redis
            if(!CollectionUtils.isEmpty(sessionData)){
                //1.缓存活动信息
                saveSessionInfo(sessionData);
                //2.缓存活动相关的商品信息
                saveSessionSkuInfo(sessionData);
            }

        }
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        //1.确定当前时间属于那个秒杀场次
        long time = new Date().getTime();
        Set<String> keys = redisFeignService.sGet(SESSION_CACHE_PREFIX + "*");
        for(String key:keys){
            String replace = key.replace("seckill:session:", "");
            String[] s = replace.split("_");
            Long start =Long.parseLong(s[0]);
            Long end=Long.parseLong(s[1]);
            if(time>=start && time<=end){
                //2.获取这个秒杀场次需要的所有商品信息
                List<String> value = redisFeignService.getValue(key);
                Map<String, Object> map = redisFeignService.getMap("seckill:skus");
                if(!CollectionUtils.isEmpty(map)){
                    //根据sku获取商品信息
                    if(!CollectionUtils.isEmpty(value)){
                        //将商品信息转换为SeckillSkuRedisTo
                        List<SeckillSkuRedisTo> collect = value.stream().map(item -> {
                            Object o = map.get(item);
                            SeckillSkuRedisTo to = JSON.parseObject(JSON.toJSONString(o), SeckillSkuRedisTo.class);
                            return to;
                        }).collect(Collectors.toList());
                        return collect;
                    }
                }



                break;
            }
        };

        return null;
    }

    private void saveSessionInfo(List<SeckillSessionsWithSkus> sessions){
        sessions.stream().forEach(session->{
            Long startTime = session.getStartTime().getTime();
            Long endTime = session.getEndTime().getTime();
            String key=SESSION_CACHE_PREFIX+startTime+"_"+endTime;
            Boolean isExist = redisFeignService.KeyIsExist(key);
            if(!isExist){
                List<String> collect = session.getRelationSkus().stream().map(item->item.getPromotionSessionId()+"_"+item.getSkuId()).collect(Collectors.toList());
                //缓存活动信息
                redisFeignService.saveList(key,collect);
            }

        });
    }

    private void saveSessionSkuInfo(List<SeckillSessionsWithSkus> sessions){
        sessions.stream().forEach(session->{
            Map<String,Object> map=new HashMap<>();
            session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                //设置随机码
                String token = UUID.randomUUID().toString().replace("-", "");
                String key=seckillSkuVo.getPromotionSessionId()+"_"+seckillSkuVo.getSkuId().toString();
                Boolean isExist = redisFeignService.KeyIsExist(key);
                if(!isExist){
                    //缓存商品信息
                    SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                    //1.sku的基本信息
                    Result result = productFeignService.info(seckillSkuVo.getSkuId());
                    if (result.getCode()==0){
                        Object object = result.get("sku");
                        SkuInfoVo sku =JSON.parseObject(JSON.toJSONString(object),new TypeReference<>() {});
                        redisTo.setSkuInfo(sku);
                    }
                    //2.sku的秒杀信息
                    BeanUtils.copyProperties(seckillSkuVo,redisTo);
                    //3.设置当前商品的秒杀时间信息
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());
                    redisTo.setRandomCode(token);
                    map.put(seckillSkuVo.getPromotionSessionId()+"_"+seckillSkuVo.getSkuId().toString(),redisTo);
                    redisFeignService.saveMap(SKUKILL_CACHE_PREFIX,map);
                    //如果当前这个场次的商品库存信息已经上架就不需要上架

                    //使用库存作为分布式的信号量；
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    //商品可以秒杀的数量作为信号量
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                }
            });
        });
    }
}

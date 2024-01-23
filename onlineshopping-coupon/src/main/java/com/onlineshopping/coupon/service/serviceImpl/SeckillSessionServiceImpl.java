package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.SeckillSession;
import com.onlineshopping.coupon.entity.SeckillSkuRelation;
import com.onlineshopping.coupon.mapper.SeckillSessionMapper;
import com.onlineshopping.coupon.service.SeckillSessionService;
import com.onlineshopping.coupon.service.SeckillSkuRelationService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SeckillSessionServiceImpl implements SeckillSessionService{

    @Resource
    private SeckillSessionMapper seckillSessionMapper;

    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return seckillSessionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SeckillSession record) {
        return seckillSessionMapper.insert(record);
    }

    @Override
    public int insertSelective(SeckillSession record) {
        return seckillSessionMapper.insertSelective(record);
    }

    @Override
    public SeckillSession selectByPrimaryKey(Long id) {
        return seckillSessionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SeckillSession record) {
        return seckillSessionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SeckillSession record) {
        return seckillSessionMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SeckillSession> getLatest3DaysSession() {
        //计算最近三天
        LocalDateTime startTime = startTime();
        LocalDateTime endTime = endTime();
        List<SeckillSession> list=seckillSessionMapper.getLatest3DaysSession(startTime,endTime);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<SeckillSession> collect = list.stream().map(session -> {
            Long id = session.getId();
            List<SeckillSkuRelation> relations=seckillSkuRelationService.getSkuIdByPromotionSessionId(id);
            session.setRelationSkus(relations);
            return session;
        }).collect(Collectors.toList());
        return collect;
    }

    private LocalDateTime startTime() {
        LocalDate now = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalDateTime start = LocalDateTime.of(now, min);
        //String format = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return start;
    }

    private LocalDateTime endTime() {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(2);
        LocalTime max = LocalTime.MAX;
        LocalDateTime end = LocalDateTime.of(localDate, max);
        //String format = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return end;
    }

}

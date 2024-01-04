package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.SeckillSession;
import com.onlineshopping.coupon.mapper.SeckillSessionMapper;
import com.onlineshopping.coupon.service.SeckillSessionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class SeckillSessionServiceImpl implements SeckillSessionService{

    @Resource
    private SeckillSessionMapper seckillSessionMapper;

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

}

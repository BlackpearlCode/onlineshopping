package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.SeckillPromotion;
import com.onlineshopping.coupon.mapper.SeckillPromotionMapper;
import com.onlineshopping.coupon.service.SeckillPromotionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class SeckillPromotionServiceImpl implements SeckillPromotionService{

    @Resource
    private SeckillPromotionMapper seckillPromotionMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return seckillPromotionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SeckillPromotion record) {
        return seckillPromotionMapper.insert(record);
    }

    @Override
    public int insertSelective(SeckillPromotion record) {
        return seckillPromotionMapper.insertSelective(record);
    }

    @Override
    public SeckillPromotion selectByPrimaryKey(Long id) {
        return seckillPromotionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SeckillPromotion record) {
        return seckillPromotionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SeckillPromotion record) {
        return seckillPromotionMapper.updateByPrimaryKey(record);
    }

}

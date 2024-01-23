package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.SeckillSkuRelation;
import com.onlineshopping.coupon.mapper.SeckillSkuRelationMapper;
import com.onlineshopping.coupon.service.SeckillSkuRelationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeckillSkuRelationServiceImpl implements SeckillSkuRelationService{

    @Resource
    private SeckillSkuRelationMapper seckillSkuRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return seckillSkuRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SeckillSkuRelation record) {
        return seckillSkuRelationMapper.insert(record);
    }

    @Override
    public int insertSelective(SeckillSkuRelation record) {
        return seckillSkuRelationMapper.insertSelective(record);
    }

    @Override
    public SeckillSkuRelation selectByPrimaryKey(Long id) {
        return seckillSkuRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SeckillSkuRelation record) {
        return seckillSkuRelationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SeckillSkuRelation record) {
        return seckillSkuRelationMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SeckillSkuRelation> getSkuIdByPromotionSessionId(Long promotionSessionId) {
        return seckillSkuRelationMapper.getSkuIdByPromotionSessionId(promotionSessionId);
    }

}

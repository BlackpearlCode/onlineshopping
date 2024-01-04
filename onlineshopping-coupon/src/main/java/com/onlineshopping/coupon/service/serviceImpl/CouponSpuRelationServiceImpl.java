package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.CouponSpuRelation;
import com.onlineshopping.coupon.mapper.CouponSpuRelationMapper;
import com.onlineshopping.coupon.service.CouponSpuRelationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class CouponSpuRelationServiceImpl implements CouponSpuRelationService{

    @Resource
    private CouponSpuRelationMapper couponSpuRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return couponSpuRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CouponSpuRelation record) {
        return couponSpuRelationMapper.insert(record);
    }

    @Override
    public int insertSelective(CouponSpuRelation record) {
        return couponSpuRelationMapper.insertSelective(record);
    }

    @Override
    public CouponSpuRelation selectByPrimaryKey(Long id) {
        return couponSpuRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CouponSpuRelation record) {
        return couponSpuRelationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CouponSpuRelation record) {
        return couponSpuRelationMapper.updateByPrimaryKey(record);
    }

}

package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.Coupon;
import com.onlineshopping.coupon.mapper.CouponMapper;
import com.onlineshopping.coupon.service.CouponService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class CouponServiceImpl implements CouponService{

    @Resource
    private CouponMapper couponMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return couponMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Coupon record) {
        return couponMapper.insert(record);
    }

    @Override
    public int insertSelective(Coupon record) {
        return couponMapper.insertSelective(record);
    }

    @Override
    public Coupon selectByPrimaryKey(Long id) {
        return couponMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Coupon record) {
        return couponMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Coupon record) {
        return couponMapper.updateByPrimaryKey(record);
    }

}

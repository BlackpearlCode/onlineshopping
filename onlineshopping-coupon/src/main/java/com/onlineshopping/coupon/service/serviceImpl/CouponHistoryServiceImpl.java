package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.CouponHistory;
import com.onlineshopping.coupon.mapper.CouponHistoryMapper;
import com.onlineshopping.coupon.service.CouponHistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class CouponHistoryServiceImpl implements CouponHistoryService{

    @Resource
    private CouponHistoryMapper couponHistoryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return couponHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CouponHistory record) {
        return couponHistoryMapper.insert(record);
    }

    @Override
    public int insertSelective(CouponHistory record) {
        return couponHistoryMapper.insertSelective(record);
    }

    @Override
    public CouponHistory selectByPrimaryKey(Long id) {
        return couponHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CouponHistory record) {
        return couponHistoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CouponHistory record) {
        return couponHistoryMapper.updateByPrimaryKey(record);
    }

}

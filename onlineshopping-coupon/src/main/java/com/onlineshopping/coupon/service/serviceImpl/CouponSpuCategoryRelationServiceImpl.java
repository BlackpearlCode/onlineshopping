package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.CouponSpuCategoryRelation;
import com.onlineshopping.coupon.mapper.CouponSpuCategoryRelationMapper;
import com.onlineshopping.coupon.service.CouponSpuCategoryRelationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class CouponSpuCategoryRelationServiceImpl implements CouponSpuCategoryRelationService{

    @Resource
    private CouponSpuCategoryRelationMapper couponSpuCategoryRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return couponSpuCategoryRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CouponSpuCategoryRelation record) {
        return couponSpuCategoryRelationMapper.insert(record);
    }

    @Override
    public int insertSelective(CouponSpuCategoryRelation record) {
        return couponSpuCategoryRelationMapper.insertSelective(record);
    }

    @Override
    public CouponSpuCategoryRelation selectByPrimaryKey(Long id) {
        return couponSpuCategoryRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CouponSpuCategoryRelation record) {
        return couponSpuCategoryRelationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CouponSpuCategoryRelation record) {
        return couponSpuCategoryRelationMapper.updateByPrimaryKey(record);
    }

}

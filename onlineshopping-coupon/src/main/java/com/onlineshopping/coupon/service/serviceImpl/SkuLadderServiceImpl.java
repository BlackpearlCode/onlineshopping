package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.SkuLadder;
import com.onlineshopping.coupon.mapper.SkuLadderMapper;
import com.onlineshopping.coupon.service.SkuLadderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class SkuLadderServiceImpl implements SkuLadderService{

    @Resource
    private SkuLadderMapper skuLadderMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return skuLadderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SkuLadder record) {
        return skuLadderMapper.insert(record);
    }

    @Override
    public int insertSelective(SkuLadder record) {
        return skuLadderMapper.insertSelective(record);
    }

    @Override
    public SkuLadder selectByPrimaryKey(Long id) {
        return skuLadderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SkuLadder record) {
        return skuLadderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SkuLadder record) {
        return skuLadderMapper.updateByPrimaryKey(record);
    }

}

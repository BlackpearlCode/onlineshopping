package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.SpuBounds;
import com.onlineshopping.coupon.mapper.SpuBoundsMapper;
import com.onlineshopping.coupon.service.SpuBoundsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class SpuBoundsServiceImpl implements SpuBoundsService{

    @Resource
    private SpuBoundsMapper spuBoundsMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return spuBoundsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SpuBounds record) {
        return spuBoundsMapper.insert(record);
    }

    @Override
    public int insertSelective(SpuBounds record) {
        return spuBoundsMapper.insertSelective(record);
    }

    @Override
    public SpuBounds selectByPrimaryKey(Long id) {
        return spuBoundsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SpuBounds record) {
        return spuBoundsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SpuBounds record) {
        return spuBoundsMapper.updateByPrimaryKey(record);
    }

}

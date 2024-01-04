package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.HomeAdv;
import com.onlineshopping.coupon.mapper.HomeAdvMapper;
import com.onlineshopping.coupon.service.HomeAdvService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class HomeAdvServiceImpl implements HomeAdvService{

    @Resource
    private HomeAdvMapper homeAdvMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return homeAdvMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HomeAdv record) {
        return homeAdvMapper.insert(record);
    }

    @Override
    public int insertSelective(HomeAdv record) {
        return homeAdvMapper.insertSelective(record);
    }

    @Override
    public HomeAdv selectByPrimaryKey(Long id) {
        return homeAdvMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(HomeAdv record) {
        return homeAdvMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HomeAdv record) {
        return homeAdvMapper.updateByPrimaryKey(record);
    }

}

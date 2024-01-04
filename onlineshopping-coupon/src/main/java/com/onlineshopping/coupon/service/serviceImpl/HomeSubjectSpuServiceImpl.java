package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.HomeSubjectSpu;
import com.onlineshopping.coupon.mapper.HomeSubjectSpuMapper;
import com.onlineshopping.coupon.service.HomeSubjectSpuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class HomeSubjectSpuServiceImpl implements HomeSubjectSpuService{

    @Resource
    private HomeSubjectSpuMapper homeSubjectSpuMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return homeSubjectSpuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HomeSubjectSpu record) {
        return homeSubjectSpuMapper.insert(record);
    }

    @Override
    public int insertSelective(HomeSubjectSpu record) {
        return homeSubjectSpuMapper.insertSelective(record);
    }

    @Override
    public HomeSubjectSpu selectByPrimaryKey(Long id) {
        return homeSubjectSpuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(HomeSubjectSpu record) {
        return homeSubjectSpuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HomeSubjectSpu record) {
        return homeSubjectSpuMapper.updateByPrimaryKey(record);
    }

}

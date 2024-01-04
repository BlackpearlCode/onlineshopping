package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.HomeSubject;
import com.onlineshopping.coupon.mapper.HomeSubjectMapper;
import com.onlineshopping.coupon.service.HomeSubjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class HomeSubjectServiceImpl implements HomeSubjectService{

    @Resource
    private HomeSubjectMapper homeSubjectMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return homeSubjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HomeSubject record) {
        return homeSubjectMapper.insert(record);
    }

    @Override
    public int insertSelective(HomeSubject record) {
        return homeSubjectMapper.insertSelective(record);
    }

    @Override
    public HomeSubject selectByPrimaryKey(Long id) {
        return homeSubjectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(HomeSubject record) {
        return homeSubjectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HomeSubject record) {
        return homeSubjectMapper.updateByPrimaryKey(record);
    }

}

package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.MemberPrice;
import com.onlineshopping.coupon.mapper.MemberPriceMapper;
import com.onlineshopping.coupon.service.MemberPriceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class MemberPriceServiceImpl implements MemberPriceService{

    @Resource
    private MemberPriceMapper memberPriceMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberPriceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberPrice record) {
        return memberPriceMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberPrice record) {
        return memberPriceMapper.insertSelective(record);
    }

    @Override
    public MemberPrice selectByPrimaryKey(Long id) {
        return memberPriceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberPrice record) {
        return memberPriceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberPrice record) {
        return memberPriceMapper.updateByPrimaryKey(record);
    }

}

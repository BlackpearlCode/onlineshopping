package com.onlineshopping.order.service.serviceImpl;

import com.onlineshopping.order.entity.PaymentInfo;
import com.onlineshopping.order.mapper.PaymentInfoMapper;
import com.onlineshopping.order.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return paymentInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PaymentInfo record) {
        return paymentInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(PaymentInfo record) {
        return paymentInfoMapper.insertSelective(record);
    }

    @Override
    public PaymentInfo selectByPrimaryKey(Long id) {
        return paymentInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PaymentInfo record) {
        return paymentInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PaymentInfo record) {
        return paymentInfoMapper.updateByPrimaryKey(record);
    }

}

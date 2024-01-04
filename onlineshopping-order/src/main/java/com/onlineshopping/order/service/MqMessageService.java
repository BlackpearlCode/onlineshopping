package com.onlineshopping.order.service;

import com.onlineshopping.order.entity.MqMessage;
public interface MqMessageService{

    int deleteByPrimaryKey(String messageId);

    int insert(MqMessage record);

    int insertSelective(MqMessage record);

    MqMessage selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(MqMessage record);

    int updateByPrimaryKey(MqMessage record);

}

package com.onlineshopping.order.mapper;

import com.onlineshopping.order.entity.MqMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MqMessageMapper {
    int deleteByPrimaryKey(String messageId);

    int insert(MqMessage record);

    int insertSelective(MqMessage record);

    MqMessage selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(MqMessage record);

    int updateByPrimaryKey(MqMessage record);
}
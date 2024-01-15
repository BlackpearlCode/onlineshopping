package com.onlineshopping.ware.mapper;

import com.onlineshopping.ware.entity.WareOrderTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WareOrderTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WareOrderTask record);

    int insertSelective(WareOrderTask record);

    WareOrderTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareOrderTask record);

    int updateByPrimaryKey(WareOrderTask record);

    WareOrderTask getOrderTaskByOrderSn(String orderSn);
}
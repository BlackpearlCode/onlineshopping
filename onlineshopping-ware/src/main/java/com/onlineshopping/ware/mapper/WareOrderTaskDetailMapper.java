package com.onlineshopping.ware.mapper;

import com.onlineshopping.ware.entity.WareOrderTaskDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WareOrderTaskDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WareOrderTaskDetail record);

    int insertSelective(WareOrderTaskDetail record);

    WareOrderTaskDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareOrderTaskDetail record);

    int updateByPrimaryKey(WareOrderTaskDetail record);

    List<WareOrderTaskDetail> getOrderTaskDetailByTaskId(Long id);
}
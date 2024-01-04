package com.onlineshopping.coupon.mapper;

import com.onlineshopping.coupon.entity.UndoLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UndoLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UndoLog record);

    int insertSelective(UndoLog record);

    UndoLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UndoLog record);

    int updateByPrimaryKey(UndoLog record);
}
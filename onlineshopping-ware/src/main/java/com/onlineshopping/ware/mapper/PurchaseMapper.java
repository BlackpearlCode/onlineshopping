package com.onlineshopping.ware.mapper;

import com.onlineshopping.ware.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

    List<Purchase> getAll(@Param("params") Map<String, Object> params);

    List<Purchase> getUnreceiveList();

    void batchUpdate(@Param("purchases") List<Purchase> purchases);

    void batchDel(@Param("ids") List<Long> ids);
}
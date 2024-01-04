package com.onlineshopping.ware.mapper;

import com.onlineshopping.ware.entity.PurchaseDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseDetail record);

    int insertSelective(PurchaseDetail record);

    PurchaseDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseDetail record);

    int updateByPrimaryKey(PurchaseDetail record);

    List<PurchaseDetail> getAll(@Param("params") Map<String, Object> params);

    void batchUpdate(@Param("purchaseDetailList") List<PurchaseDetail> purchaseDetailList);

    List<PurchaseDetail> listDetailById(@Param("ids") List<Long> ids);


    void batchDelete(@Param("ids") List<Long> ids);
}
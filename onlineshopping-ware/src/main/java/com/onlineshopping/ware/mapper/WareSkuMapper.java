package com.onlineshopping.ware.mapper;

import com.onlineshopping.ware.entity.WareSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WareSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WareSku record);

    int insertSelective(WareSku record);

    WareSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareSku record);

    int updateByPrimaryKey(WareSku record);

    List<WareSku> getAll(@Param("params") Map<String, Object> params);

    WareSku selectBySkuIdAndWareId(Long skuId, Long wareId);


    int selectBySkuId(Long skuId);

    WareSku selectInfoBySkuId(Long skuId);

    List<Long> listWareIdHasStock(Long skuId);

    Long lockStock(@Param("skuId") Long skuId,@Param("wareId") Long wareId,@Param("num") Integer num);

    void unlockStock(@Param("skuId") Long skuId,@Param("wareId") Long wareId,@Param("num") Integer num);
}
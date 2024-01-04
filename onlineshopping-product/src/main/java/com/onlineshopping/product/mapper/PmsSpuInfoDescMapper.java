package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsSpuInfoDesc;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PmsSpuInfoDescMapper {
    int deleteByPrimaryKey(Long spuId);

    int insert(PmsSpuInfoDesc record);

    int insertSelective(PmsSpuInfoDesc record);

    PmsSpuInfoDesc selectByPrimaryKey(Long spuId);

    int updateByPrimaryKeySelective(PmsSpuInfoDesc record);

    int updateByPrimaryKey(PmsSpuInfoDesc record);


}
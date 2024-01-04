package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsProductAttrValue;
import com.onlineshopping.product.vo.ListForspuIdVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsProductAttrValueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsProductAttrValue record);

    int insertSelective(PmsProductAttrValue record);

    PmsProductAttrValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsProductAttrValue record);

    int updateByPrimaryKey(PmsProductAttrValue record);

    void batchSave(@Param("productAttrValues") List<PmsProductAttrValue> productAttrValues);

    List<PmsProductAttrValue> selectBySpuId(Long spuId);

    void updateBySpuIds(@Param("vo") List<ListForspuIdVo> vo);
}
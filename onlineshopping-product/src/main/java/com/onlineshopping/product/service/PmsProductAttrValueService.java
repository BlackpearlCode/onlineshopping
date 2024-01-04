package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsProductAttrValue;
import com.onlineshopping.product.vo.BaseAttrs;
import com.onlineshopping.product.vo.ListForspuIdVo;

import java.util.List;

public interface PmsProductAttrValueService{


    int deleteByPrimaryKey(Long id);

    int insert(PmsProductAttrValue record);

    int insertSelective(PmsProductAttrValue record);

    PmsProductAttrValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsProductAttrValue record);

    int updateByPrimaryKey(PmsProductAttrValue record);

    void save(Long id,List<BaseAttrs> baseAttrs);

    List<PmsProductAttrValue> selectBySpuId(Long spuId);

    void updateBySpuIds(List<ListForspuIdVo> vo);
}

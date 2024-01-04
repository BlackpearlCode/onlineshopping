package com.onlineshopping.product.service;

import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsAttr;
import com.onlineshopping.product.vo.AttrInfoVo;
import com.onlineshopping.product.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Set;

public interface PmsAttrService{


    int deleteByPrimaryKey(Long attrId);

    int insert(PmsAttr record);

    int insertSelective(PmsAttr record);

    PmsAttr selectByPrimaryKey(Long attrId);

    int updateByPrimaryKeySelective(PmsAttr record);

    int updateByPrimaryKey(PmsAttr record);


    PageEntity selectAll( int page, int limit,String key,Long catelogId,String attrType);

    AttrInfoVo selectAttrInfo(Long attrId);

    List<AttrInfoVo> selectBatchByAttrIds(List<Long> attrIds);

    PageEntity getRelation(long attrgroupId, int page, int limit, String key);

    //根据attrid批量删除
    void batchDeleteByAttrId(List<Long> attrIds);

    //查询可以被检索的规格属性
    Set<Long> selectBySearchType(Long type);

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long catalogId, Long spuId);
}

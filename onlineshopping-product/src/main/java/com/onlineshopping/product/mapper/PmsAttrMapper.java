package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsAttr;
import com.onlineshopping.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface PmsAttrMapper {
    int deleteByPrimaryKey(Long attrId);

    int insert(PmsAttr record);

    int insertSelective(PmsAttr record);

    PmsAttr selectByPrimaryKey(Long attrId);

    int updateByPrimaryKeySelective(PmsAttr record);

    int updateByPrimaryKey(PmsAttr record);

    List<PmsAttr> selectByKey(String key,String attrType);
    List<PmsAttr> selectByKeyAndCatelogId(Long catelogId,String key,String attrType);
    List<PmsAttr> selectByAttrNameOrId(String key, long catelogId);

    List<PmsAttr> selectBatchByAttrIds(@Param("ids")List<Long> ids);


    List<PmsAttr> selectRelation(Long catelogId,@Param("attrIdList") List<Long> attrIdList,String key,int attrType);


    List<PmsAttr> selectByAttrType(int attrType,String key);

    void batchDeleteByAttrId(@Param("attrIds") List<Long> attrIds);

    PmsAttr selectAttrNameByAttrIdAndAttrType(Long item, int attrType);

    Set<Long> selectBySearchType(Long type);

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("catalogId") Long catalogId, @Param("spuId") Long spuId);
}
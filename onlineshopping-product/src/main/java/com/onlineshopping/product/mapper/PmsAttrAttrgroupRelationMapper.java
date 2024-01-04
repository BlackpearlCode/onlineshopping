package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsAttrAttrgroupRelation;
import com.onlineshopping.product.vo.AttrGroupRelationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsAttrAttrgroupRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsAttrAttrgroupRelation record);

    int insertSelective(PmsAttrAttrgroupRelation record);

    PmsAttrAttrgroupRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsAttrAttrgroupRelation record);

    int updateByPrimaryKey(PmsAttrAttrgroupRelation record);

    PmsAttrAttrgroupRelation selectByAttrId(Long attrId);


    void updateByAttrId(Long attrGroupId, Long attrId);

    List<Long> selectByGroupId(Long attrGroupId);

    void deleteBatch(@Param("relationVo") AttrGroupRelationVo[]  relationVo);

    List<PmsAttrAttrgroupRelation> selectByIdList(@Param("attrGroupIdList")List<Long> attrGroupIdList);

    void batchInsert(@Param("relationVo")List<PmsAttrAttrgroupRelation> relationVo);
}
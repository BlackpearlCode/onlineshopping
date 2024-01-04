package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsAttrGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsAttrGroupMapper {
    int deleteByPrimaryKey(Long attrGroupId);

    int insert(PmsAttrGroup record);

    int insertSelective(PmsAttrGroup record);

    PmsAttrGroup selectByPrimaryKey(Long attrGroupId);

    int updateByPrimaryKeySelective(PmsAttrGroup record);

    int updateByPrimaryKey(PmsAttrGroup record);

    //根据所属分类ID查询
    List<PmsAttrGroup> selectByAttrGroupId(Long attrGroupId);

    //根据attr_group_name或attr_group_id查询
    List<PmsAttrGroup> selectByAttrGroupNameOrId(String key,long catelogId);

    List<PmsAttrGroup> selectAll(@Param("key") String key);

    //批量删除

    int batchDelete(List<Long> attrGroupIds);

    List<PmsAttrGroup> selectByCatelogId( Long catelogId);
}
package com.onlineshopping.product.service;

import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsAttrGroup;
import com.onlineshopping.product.vo.AttrGroupWithAttrsVo;

import java.util.List;

public interface PmsAttrGroupService{


    int deleteByPrimaryKey(Long attrGroupId);

    int insert(PmsAttrGroup record);

    int insertSelective(PmsAttrGroup record);

    PmsAttrGroup selectByPrimaryKey(Long attrGroupId);

    int updateByPrimaryKeySelective(PmsAttrGroup record);

    int updateByPrimaryKey(PmsAttrGroup record);

    //查询三级分类
    PageEntity selectAll(long catelogId,int page, int limit, String key);

    //批量删除
    int batchDelete(Long[] attrGroupIds);

    //根据id查询商品的三级分类完整路径

    Long[] catelogPath(long catId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}

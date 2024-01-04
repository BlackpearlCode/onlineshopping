package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsCategory;
import com.onlineshopping.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

public interface PmsCategoryService{


    int deleteByPrimaryKey(Long catId);

    int insert(PmsCategory record);

    int insertSelective(PmsCategory record);

    PmsCategory selectByPrimaryKey(Long catId);

    int updateByPrimaryKeySelective(PmsCategory record);

    int updateByPrimaryKey(PmsCategory record);

    List<PmsCategory> selectlist();

    List<PmsCategory> seletcListTree();

    //批量删除(逻辑删除)
    int updateShowStatusList(List ids);

    List<PmsCategory> getLevelCategorys(Long parentId);

    //三级分类

    Map<String, List<Catelog2Vo>> getCatalogJson();
}

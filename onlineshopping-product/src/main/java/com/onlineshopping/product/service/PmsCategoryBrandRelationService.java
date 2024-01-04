package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsCategoryBrandRelation;

import java.util.List;

public interface PmsCategoryBrandRelationService{


    int deleteByPrimaryKey(Long id);

    int insert(PmsCategoryBrandRelation record);

    int insertSelective(PmsCategoryBrandRelation record);

    PmsCategoryBrandRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsCategoryBrandRelation record);

    int updateByPrimaryKey(PmsCategoryBrandRelation record);

    List<PmsCategoryBrandRelation> selectByBrandId(Long brandId);

    int batchDelete(List<Long> ids);

    int updateByBrandId(Long brandId,String brandName);

    int updateByCatelogId(Long catelogId,String catelogName);

    int deleteByCatelogId(List<Long> catelogIds);

    int deleteByBrandId(List<Long> brandIds);

    List<PmsCategoryBrandRelation> getBrandsByCatelogId(Long catId);
}

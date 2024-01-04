package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsCategoryBrandRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsCategoryBrandRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsCategoryBrandRelation record);

    int insertSelective(PmsCategoryBrandRelation record);

    PmsCategoryBrandRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsCategoryBrandRelation record);

    int updateByPrimaryKey(PmsCategoryBrandRelation record);

    List<PmsCategoryBrandRelation> selectByBradnId(Long brandId);

    int batchDelete(List<Long> ids);

    int updateByBrandId(Long brandId,String brandName);

    int updateByCatelogId(Long catelogId,String catelogName);

    int deleteByCatelogIds(List<Long> catelogIds);

    int deleteByBrands(List<Long> brandIds);

    List<PmsCategoryBrandRelation> getBrandsByCatelogId( Long catId);
}
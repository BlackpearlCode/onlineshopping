package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsSkuImages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsSkuImagesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsSkuImages record);

    int insertSelective(PmsSkuImages record);

    PmsSkuImages selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsSkuImages record);

    int updateByPrimaryKey(PmsSkuImages record);

    void batchSave(@Param("imagesList") List<PmsSkuImages> imagesList);

    List<PmsSkuImages> selectBySkuId(Long skuId);
}
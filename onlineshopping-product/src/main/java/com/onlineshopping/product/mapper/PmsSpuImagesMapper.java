package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsSpuImages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsSpuImagesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsSpuImages record);

    int insertSelective(PmsSpuImages record);

    PmsSpuImages selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsSpuImages record);

    int updateByPrimaryKey(PmsSpuImages record);


    void batchSave(@Param("images") List<PmsSpuImages> images);
}
package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsSpuImages;

import java.util.List;

public interface PmsSpuImagesService{


    int deleteByPrimaryKey(Long id);

    int insert(PmsSpuImages record);

    int insertSelective(PmsSpuImages record);

    PmsSpuImages selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsSpuImages record);

    int updateByPrimaryKey(PmsSpuImages record);

    //批量保存图片信息
    void batchSave(Long id, List<String> images);
}

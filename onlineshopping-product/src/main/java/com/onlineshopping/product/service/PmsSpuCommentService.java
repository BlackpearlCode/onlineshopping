package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsSpuComment;

public interface PmsSpuCommentService{


    int deleteByPrimaryKey(Long id);

    int insert(PmsSpuComment record);

    int insertSelective(PmsSpuComment record);

    PmsSpuComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsSpuComment record);

    int updateByPrimaryKey(PmsSpuComment record);

}

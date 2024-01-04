package com.onlineshopping.product.service;

import com.onlineshopping.product.entity.PmsCommentReplay;

public interface PmsCommentReplayService{


    int deleteByPrimaryKey(Long id);

    int insert(PmsCommentReplay record);

    int insertSelective(PmsCommentReplay record);

    PmsCommentReplay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsCommentReplay record);

    int updateByPrimaryKey(PmsCommentReplay record);

}

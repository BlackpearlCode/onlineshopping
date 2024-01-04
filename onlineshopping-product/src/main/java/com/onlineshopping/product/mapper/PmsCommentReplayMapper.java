package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsCommentReplay;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PmsCommentReplayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsCommentReplay record);

    int insertSelective(PmsCommentReplay record);

    PmsCommentReplay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsCommentReplay record);

    int updateByPrimaryKey(PmsCommentReplay record);
}
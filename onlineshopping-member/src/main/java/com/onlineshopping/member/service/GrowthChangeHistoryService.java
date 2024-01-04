package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.GrowthChangeHistory;
public interface GrowthChangeHistoryService{


    int deleteByPrimaryKey(Long id);

    int insert(GrowthChangeHistory record);

    int insertSelective(GrowthChangeHistory record);

    GrowthChangeHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GrowthChangeHistory record);

    int updateByPrimaryKey(GrowthChangeHistory record);

}

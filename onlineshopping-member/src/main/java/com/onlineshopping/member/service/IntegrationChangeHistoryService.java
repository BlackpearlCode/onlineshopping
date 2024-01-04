package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.IntegrationChangeHistory;
public interface IntegrationChangeHistoryService{


    int deleteByPrimaryKey(Long id);

    int insert(IntegrationChangeHistory record);

    int insertSelective(IntegrationChangeHistory record);

    IntegrationChangeHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntegrationChangeHistory record);

    int updateByPrimaryKey(IntegrationChangeHistory record);

}

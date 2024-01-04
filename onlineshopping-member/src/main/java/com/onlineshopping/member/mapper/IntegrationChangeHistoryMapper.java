package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.IntegrationChangeHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IntegrationChangeHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IntegrationChangeHistory record);

    int insertSelective(IntegrationChangeHistory record);

    IntegrationChangeHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntegrationChangeHistory record);

    int updateByPrimaryKey(IntegrationChangeHistory record);
}
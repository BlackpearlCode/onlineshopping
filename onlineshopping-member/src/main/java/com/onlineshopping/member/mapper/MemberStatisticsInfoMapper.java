package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.MemberStatisticsInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberStatisticsInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberStatisticsInfo record);

    int insertSelective(MemberStatisticsInfo record);

    MemberStatisticsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberStatisticsInfo record);

    int updateByPrimaryKey(MemberStatisticsInfo record);
}
package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.MemberStatisticsInfo;
public interface MemberStatisticsInfoService{


    int deleteByPrimaryKey(Long id);

    int insert(MemberStatisticsInfo record);

    int insertSelective(MemberStatisticsInfo record);

    MemberStatisticsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberStatisticsInfo record);

    int updateByPrimaryKey(MemberStatisticsInfo record);

}

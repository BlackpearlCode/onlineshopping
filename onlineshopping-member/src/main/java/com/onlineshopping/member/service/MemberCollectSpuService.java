package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.MemberCollectSpu;
public interface MemberCollectSpuService{


    int deleteByPrimaryKey(Long id);

    int insert(MemberCollectSpu record);

    int insertSelective(MemberCollectSpu record);

    MemberCollectSpu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberCollectSpu record);

    int updateByPrimaryKey(MemberCollectSpu record);

}

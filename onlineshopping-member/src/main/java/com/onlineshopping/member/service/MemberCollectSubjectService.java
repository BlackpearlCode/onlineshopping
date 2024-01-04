package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.MemberCollectSubject;
public interface MemberCollectSubjectService{


    int deleteByPrimaryKey(Long id);

    int insert(MemberCollectSubject record);

    int insertSelective(MemberCollectSubject record);

    MemberCollectSubject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberCollectSubject record);

    int updateByPrimaryKey(MemberCollectSubject record);

}

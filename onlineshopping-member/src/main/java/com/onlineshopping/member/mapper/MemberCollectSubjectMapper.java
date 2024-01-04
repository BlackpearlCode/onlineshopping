package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.MemberCollectSubject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberCollectSubjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberCollectSubject record);

    int insertSelective(MemberCollectSubject record);

    MemberCollectSubject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberCollectSubject record);

    int updateByPrimaryKey(MemberCollectSubject record);
}
package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.MemberLoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberLoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberLoginLog record);

    int insertSelective(MemberLoginLog record);

    MemberLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberLoginLog record);

    int updateByPrimaryKey(MemberLoginLog record);
}
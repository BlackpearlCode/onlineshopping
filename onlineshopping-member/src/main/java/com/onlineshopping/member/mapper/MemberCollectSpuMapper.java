package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.MemberCollectSpu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberCollectSpuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberCollectSpu record);

    int insertSelective(MemberCollectSpu record);

    MemberCollectSpu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberCollectSpu record);

    int updateByPrimaryKey(MemberCollectSpu record);
}
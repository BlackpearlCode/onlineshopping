package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.MemberReceiveAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberReceiveAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberReceiveAddress record);

    int insertSelective(MemberReceiveAddress record);

    MemberReceiveAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberReceiveAddress record);

    int updateByPrimaryKey(MemberReceiveAddress record);

    List<MemberReceiveAddress> selectByMemberId(Long memberId);
}
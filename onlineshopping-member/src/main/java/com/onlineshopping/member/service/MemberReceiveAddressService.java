package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.MemberReceiveAddress;

import java.util.List;

public interface MemberReceiveAddressService{


    int deleteByPrimaryKey(Long id);

    int insert(MemberReceiveAddress record);

    int insertSelective(MemberReceiveAddress record);

    MemberReceiveAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberReceiveAddress record);

    int updateByPrimaryKey(MemberReceiveAddress record);

    // 获取用户的所有收货地址
    List<MemberReceiveAddress> getAddress(Long memberId);
}

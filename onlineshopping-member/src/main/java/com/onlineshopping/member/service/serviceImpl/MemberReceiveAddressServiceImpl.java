package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.MemberReceiveAddress;
import com.onlineshopping.member.mapper.MemberReceiveAddressMapper;
import com.onlineshopping.member.service.MemberReceiveAddressService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MemberReceiveAddressServiceImpl implements MemberReceiveAddressService{

    @Resource
    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberReceiveAddressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberReceiveAddress record) {
        return memberReceiveAddressMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberReceiveAddress record) {
        return memberReceiveAddressMapper.insertSelective(record);
    }

    @Override
    public MemberReceiveAddress selectByPrimaryKey(Long id) {
        return memberReceiveAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberReceiveAddress record) {
        return memberReceiveAddressMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberReceiveAddress record) {
        return memberReceiveAddressMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MemberReceiveAddress> getAddress(Long memberId) {
        if(memberId!=null){
            return memberReceiveAddressMapper.selectByMemberId(memberId);
        }
        return null;
    }

}

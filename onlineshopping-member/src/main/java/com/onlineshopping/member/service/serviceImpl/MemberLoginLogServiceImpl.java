package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.MemberLoginLog;
import com.onlineshopping.member.mapper.MemberLoginLogMapper;
import com.onlineshopping.member.service.MemberLoginLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberLoginLogServiceImpl implements MemberLoginLogService{

    @Resource
    private MemberLoginLogMapper memberLoginLogMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberLoginLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberLoginLog record) {
        return memberLoginLogMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberLoginLog record) {
        return memberLoginLogMapper.insertSelective(record);
    }

    @Override
    public MemberLoginLog selectByPrimaryKey(Long id) {
        return memberLoginLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberLoginLog record) {
        return memberLoginLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberLoginLog record) {
        return memberLoginLogMapper.updateByPrimaryKey(record);
    }

}

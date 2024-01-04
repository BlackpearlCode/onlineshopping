package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.MemberCollectSubject;
import com.onlineshopping.member.mapper.MemberCollectSubjectMapper;
import com.onlineshopping.member.service.MemberCollectSubjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class MemberCollectSubjectServiceImpl implements MemberCollectSubjectService{

    @Resource
    private MemberCollectSubjectMapper memberCollectSubjectMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberCollectSubjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberCollectSubject record) {
        return memberCollectSubjectMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberCollectSubject record) {
        return memberCollectSubjectMapper.insertSelective(record);
    }

    @Override
    public MemberCollectSubject selectByPrimaryKey(Long id) {
        return memberCollectSubjectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberCollectSubject record) {
        return memberCollectSubjectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberCollectSubject record) {
        return memberCollectSubjectMapper.updateByPrimaryKey(record);
    }

}

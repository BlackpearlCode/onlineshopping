package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.MemberStatisticsInfo;
import com.onlineshopping.member.mapper.MemberStatisticsInfoMapper;
import com.onlineshopping.member.service.MemberStatisticsInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberStatisticsInfoServiceImpl implements MemberStatisticsInfoService{

    @Resource
    private MemberStatisticsInfoMapper memberStatisticsInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberStatisticsInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberStatisticsInfo record) {
        return memberStatisticsInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberStatisticsInfo record) {
        return memberStatisticsInfoMapper.insertSelective(record);
    }

    @Override
    public MemberStatisticsInfo selectByPrimaryKey(Long id) {
        return memberStatisticsInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberStatisticsInfo record) {
        return memberStatisticsInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberStatisticsInfo record) {
        return memberStatisticsInfoMapper.updateByPrimaryKey(record);
    }

}

package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.MemberCollectSpu;
import com.onlineshopping.member.mapper.MemberCollectSpuMapper;
import com.onlineshopping.member.service.MemberCollectSpuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class MemberCollectSpuServiceImpl implements MemberCollectSpuService{

    @Resource
    private MemberCollectSpuMapper memberCollectSpuMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberCollectSpuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberCollectSpu record) {
        return memberCollectSpuMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberCollectSpu record) {
        return memberCollectSpuMapper.insertSelective(record);
    }

    @Override
    public MemberCollectSpu selectByPrimaryKey(Long id) {
        return memberCollectSpuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberCollectSpu record) {
        return memberCollectSpuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberCollectSpu record) {
        return memberCollectSpuMapper.updateByPrimaryKey(record);
    }

}

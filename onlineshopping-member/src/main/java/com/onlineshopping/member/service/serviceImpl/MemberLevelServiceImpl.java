package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.MemberLevel;
import com.onlineshopping.member.mapper.MemberLevelMapper;
import com.onlineshopping.member.service.MemberLevelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.common.utils.PageEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MemberLevelServiceImpl implements MemberLevelService{

    @Resource
    private MemberLevelMapper memberLevelMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberLevelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberLevel record) {
        return memberLevelMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberLevel record) {
        return memberLevelMapper.insertSelective(record);
    }

    @Override
    public MemberLevel selectByPrimaryKey(Long id) {
        return memberLevelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberLevel record) {
        return memberLevelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberLevel record) {
        return memberLevelMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity selectAll(String key, int page, int limit) {

        List<MemberLevel> memberLevels=memberLevelMapper.selectAll(key);
        PageHelper.startPage(page,limit);
        PageInfo<MemberLevel> pageInfo = new PageInfo<>(memberLevels);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
        return pageEntity;
    }

    @Override
    public void batchById(List<Long> ids) {
        memberLevelMapper.batchDeleteById(ids);
    }

    @Override
    public MemberLevel getDefaultLevel() {
        return memberLevelMapper.getDefaultLevel();

    }

}

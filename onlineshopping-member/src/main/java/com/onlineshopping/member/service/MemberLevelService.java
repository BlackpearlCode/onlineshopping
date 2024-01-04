package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.MemberLevel;
import com.onlineshopping.common.utils.PageEntity;

import java.util.List;

public interface MemberLevelService{


    int deleteByPrimaryKey(Long id);

    int insert(MemberLevel record);

    int insertSelective(MemberLevel record);

    MemberLevel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberLevel record);

    int updateByPrimaryKey(MemberLevel record);

    PageEntity selectAll(String key, int page, int limit);

    void batchById(List<Long> ids);

    //获取会员默认的等级
    MemberLevel getDefaultLevel();
}

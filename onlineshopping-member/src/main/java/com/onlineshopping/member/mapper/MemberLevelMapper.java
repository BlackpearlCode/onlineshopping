package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.MemberLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberLevelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberLevel record);

    int insertSelective(MemberLevel record);

    MemberLevel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberLevel record);

    int updateByPrimaryKey(MemberLevel record);

    List<MemberLevel> selectAll(String key);

    void batchDeleteById(@Param("ids")List<Long> ids);

    MemberLevel getDefaultLevel();
}
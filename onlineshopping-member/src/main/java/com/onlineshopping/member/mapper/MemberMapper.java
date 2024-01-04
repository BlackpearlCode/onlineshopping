package com.onlineshopping.member.mapper;

import com.onlineshopping.member.entity.Member;
import com.onlineshopping.member.vo.MemberLoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    int selectPhoneISExist(String phone);

    int seleatUsernameISExist(String username);

    Member selectUsernameOrPhone(@Param("vo") MemberLoginVo vo);


    Member selectByMemberId(@Param("memberId") String memberId);

    Member selectBySocialUid(@Param("socialUid") String socialUid);
}
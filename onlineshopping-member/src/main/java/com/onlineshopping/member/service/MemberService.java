package com.onlineshopping.member.service;

import com.onlineshopping.member.entity.Member;
import com.onlineshopping.member.exception.PhoneExistException;
import com.onlineshopping.member.exception.UsernameExistException;
import com.onlineshopping.member.vo.MemberLoginVo;
import com.onlineshopping.member.vo.MemberRegistVo;
import com.onlineshopping.member.vo.Oauth2UserInfo;

import java.text.ParseException;

public interface MemberService{


    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    void regist(MemberRegistVo vo);
    //校验手机号是否唯一
    void checkPhoneUnique(String phone) throws PhoneExistException;
    //校验用户名是否唯一
    void checkUsernameUnique(String username) throws UsernameExistException;

    Member login(MemberLoginVo vo);

    Member oauth2Login(Oauth2UserInfo userInfo) throws ParseException;

    //通过用户id或者社交账号唯一id查询用户信息
    Member selectByMemberId(String memberId);
}

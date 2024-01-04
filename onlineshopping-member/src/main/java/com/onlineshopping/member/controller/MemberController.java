package com.onlineshopping.member.controller;

import com.onlineshopping.member.entity.Member;
import com.onlineshopping.member.exception.PhoneExistException;
import com.onlineshopping.member.exception.UsernameExistException;
import com.onlineshopping.member.service.MemberService;
import com.onlineshopping.member.vo.MemberLoginVo;
import com.onlineshopping.member.vo.MemberRegistVo;
import com.onlineshopping.member.vo.Oauth2UserInfo;
import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.common.vo.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RequestMapping("member/member")
@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    //会员注册
    @RequestMapping("/regist")
    public Result regist(@RequestBody MemberRegistVo vo){
        try {
            memberService.regist(vo);
        }catch (PhoneExistException e){
            return Result.error(BizCodeEnum.PHONE_EXISTS_EXCEPTION);
        }catch (UsernameExistException e){
            return Result.error(BizCodeEnum.USER_EXISTS_EXCEPTION);
        }
        return Result.ok();
    }

    //会员登录
    @PostMapping("/login")
    public Result login(@RequestBody MemberLoginVo vo){
        Member member=memberService.login(vo);
        if(member!=null){
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setNickname(member.getNickname());
            tokenInfo.setUserId(String.valueOf(member.getId()));
            return Result.ok().put("data",tokenInfo);
        }
        return Result.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION);
    }

    //第三方社交登录
    @RequestMapping("/oauth2/login")
    public Result oauth2Login(@RequestBody Oauth2UserInfo userInfo) throws ParseException {
        if(userInfo==null){
            return Result.error(BizCodeEnum.OAUTH2_LOGIN_EXCEPTION);
        }
        Member member=memberService.oauth2Login(userInfo);
        if(member!=null){
            return Result.ok();
        }
        return Result.error(BizCodeEnum.OAUTH2_LOGIN_EXCEPTION);
    }

    /**
     * 通过社交账号查询会员id
     * @param memberId
     * @return
     */
    @RequestMapping("/findMemberId")
    public Member findMemberId(@RequestParam("memberId") String memberId){
        return memberService.selectByMemberId(memberId);
    }
}

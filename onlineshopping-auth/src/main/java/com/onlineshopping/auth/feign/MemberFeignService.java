package com.onlineshopping.auth.feign;


import com.onlineshopping.auth.vo.Oauth2UserInfo;
import com.onlineshopping.auth.vo.UserLoginVo;
import com.onlineshopping.auth.vo.UserRegistVo;
import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;

@FeignClient(name = "onlineshopping-member")
public interface MemberFeignService {

    @RequestMapping("/member/member/regist")
    Result regist(@RequestBody UserRegistVo vo);

    @PostMapping("/member/member/login")
    Result login(@RequestBody UserLoginVo vo);

    @RequestMapping("/member/member/oauth2/login")
    Result oauth2Login(@RequestBody Oauth2UserInfo userInfo) throws ParseException;
}

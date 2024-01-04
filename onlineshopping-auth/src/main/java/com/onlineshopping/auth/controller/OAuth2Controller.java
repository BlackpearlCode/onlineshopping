package com.onlineshopping.auth.controller;

import com.alipay.api.AlipayApiException;
import com.onlineshopping.auth.feign.MemberFeignService;
import com.onlineshopping.auth.feign.ThirdPartyFeginService;
import com.onlineshopping.auth.vo.Oauth2UserInfo;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.common.vo.TokenInfo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;

/**
 * 处理社交登录请求
 */
@Controller
@Slf4j
public class OAuth2Controller {

    @Autowired
    private ThirdPartyFeginService thirdPartyFeginService;
    @Autowired
    private MemberFeignService memberFeignService;

    @RequestMapping("/oauth2.0/zhifubao/success")
    public String zhifubao(@RequestParam("auth_code") String auth_code, HttpSession session) throws AlipayApiException, ParseException {
        Oauth2UserInfo oauth2UserInfo = thirdPartyFeginService.oauth2Login(auth_code);
        if(oauth2UserInfo==null){
            log.error("获取用户信息失败");
            return "redirect:http://auth.onlineshopping.com/login.html";
        }
        Result result = memberFeignService.oauth2Login(oauth2UserInfo);
        if(result.getCode()==0){
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setSocialUid(oauth2UserInfo.getOpenId());
            tokenInfo.setNickname(oauth2UserInfo.getNickname());
            session.setAttribute("loginUser",tokenInfo);
            return "redirect:http://onlineshopping.com";
        }else{
            return "redirect:http://auth.onlineshopping.com/login.html";
        }

    }
}

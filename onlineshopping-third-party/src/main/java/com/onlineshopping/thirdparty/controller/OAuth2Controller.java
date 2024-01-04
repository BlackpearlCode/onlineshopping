package com.onlineshopping.thirdparty.controller;

import com.alipay.api.AlipayApiException;
import com.onlineshopping.thirdparty.service.GetAuthTokenService;
import com.onlineshopping.thirdparty.vo.Oauth2UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauth2")
@Slf4j
public class OAuth2Controller {

    @Autowired
    private GetAuthTokenService getAuthTokenService;

    @RequestMapping("/login")
    public Oauth2UserInfo oauth2Login(@RequestParam("auth_code") String auth_code) throws AlipayApiException {
        //根据code，获取token及用户信息
        Oauth2UserInfo userInfo = getAuthTokenService.getUserInfoByAccessToken(auth_code);

        return userInfo;
    }

}

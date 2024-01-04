package com.onlineshopping.thirdparty.service;

import com.alipay.api.AlipayApiException;
import com.onlineshopping.thirdparty.vo.Oauth2UserInfo;

public interface GetAuthTokenService {

    //通过code获取token，在通过token获取用户信息
    Oauth2UserInfo getUserInfoByAccessToken(String code) throws AlipayApiException;


}

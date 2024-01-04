package com.onlineshopping.thirdparty.service;

import com.onlineshopping.common.utils.Result;

public interface SendMessageService {
    //发送验证码
    Result sendCode(String phone, String code) throws Exception;
}

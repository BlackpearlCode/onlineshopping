package com.onlineshopping.thirdparty.service.impl;

import com.onlineshopping.common.utils.Result;
import com.onlineshopping.thirdparty.service.SendMessageService;
import com.onlineshopping.thirdparty.tools.SendCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl implements SendMessageService {
    @Autowired
    private SendCode sendCode;
    @Override
    public Result sendCode(String phone, String code) throws Exception {
        sendCode.sendCode(phone, code);
        return Result.ok();
    }
}

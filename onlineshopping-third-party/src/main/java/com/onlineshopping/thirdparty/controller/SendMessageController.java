package com.onlineshopping.thirdparty.controller;

import com.onlineshopping.common.utils.Result;
import com.onlineshopping.thirdparty.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sms")
public class SendMessageController {

    @Autowired
    private SendMessageService sendMessageService;

    /**
     * 短信发送验证码
     * @param phone 手机号
     * @param code 验证码
     * @return
     * @throws Exception
     */
    @GetMapping("/sendCode")
    Result sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) throws Exception {
        // 发送短信
        return  sendMessageService.sendCode(phone, code);
    }




}

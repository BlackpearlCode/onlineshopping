package com.onlineshopping.auth.feign;

import com.alipay.api.AlipayApiException;
import com.onlineshopping.auth.vo.Oauth2UserInfo;
import com.onlineshopping.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "third-party")
public interface ThirdPartyFeginService {

    @GetMapping("/sms/sendCode")
    Result sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) throws Exception;

    @RequestMapping("/oauth2/login")
    Oauth2UserInfo oauth2Login(@RequestParam("auth_code") String auth_code) throws AlipayApiException;
}

package com.onlineshopping.thirdparty.tools;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.alipay.api.AlipayConstants.CHARSET_UTF8;


/**
 * 阿里云三方登录配置类
 */
@Slf4j
@Component
public class AlipaySystemOauthToken {

    /**
     * 网关地址
     */

    @Value("${spring.cloud.alicloud.oauth.SERVICE_URL}")
    public String SERVICE_URL;

    /**
     * appid
     */
    @Value("${spring.cloud.alicloud.oauth.APPID}")
    public String APPID;


    /**
     * 支付宝公钥（在签名）
     */
    @Value("${spring.cloud.alicloud.oauth.ALIPAY_PUBLIC_KEY}")
    public String ALIPAY_PUBLIC_KEY;

    /**
     * 你自己生成的私钥
     */
    @Value("${spring.cloud.alicloud.oauth.APP_PRIVATE_KEY}")
    public String APP_PRIVATE_KEY ;






    /**
     * 获取token信息
     */
    public  AlipaySystemOauthTokenResponse getAuthToken(String code) throws AlipayApiException{
        AlipayClient alipayClient = getAlipayClient();
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        response.setExpiresIn("3600");
        //获取token成功
        if(response.isSuccess()){
            log.info("获取token成功");
            return response;
        }
        log.error("获取token失败",response.getSubMsg());
        return null;
    }

    /**
     * 根据token获取用户信息
     */
    public  AlipayUserInfoShareResponse getUserInfo(String token) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse responseBody = alipayClient.execute(userInfoShareRequest, token);
        return responseBody;
    }

    @NotNull
    private  AlipayClient getAlipayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl(SERVICE_URL);
        //设置应用ID
        alipayConfig.setAppId(APPID);
        //设置应用私钥
        alipayConfig.setPrivateKey(APP_PRIVATE_KEY);
        //设置请求格式，固定值json
        alipayConfig.setFormat("json");
        //设置字符集
        alipayConfig.setCharset(CHARSET_UTF8);
        //设置签名类型
        alipayConfig.setSignType("RSA2");
        //设置支付宝公钥
        alipayConfig.setAlipayPublicKey(ALIPAY_PUBLIC_KEY);
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        return alipayClient;
    }




}

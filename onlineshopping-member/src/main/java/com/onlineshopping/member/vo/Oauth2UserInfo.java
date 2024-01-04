package com.onlineshopping.member.vo;

import lombok.Data;

@Data
public class Oauth2UserInfo {
    //获取社交用户唯一标识id
    private String openId;
    //获取用户令牌（token）
    private String accessToken ;
    //获取访问令牌的有效时间
    private long expiresIn ;
    //用户名
    private String nickname;

}

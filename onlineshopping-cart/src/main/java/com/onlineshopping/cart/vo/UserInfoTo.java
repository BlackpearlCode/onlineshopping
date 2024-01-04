package com.onlineshopping.cart.vo;

import lombok.Data;

@Data
public class UserInfoTo {
    //用户id或社交用户唯一标识id
    private String userId;
    private String userKey;
    //是否有临时用户；true：有；false：没有
    private boolean tempUser = false;
}

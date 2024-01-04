package com.onlineshopping.common.utils;


public enum BizCodeEnum {

    SMS_CODE_EXCEPTION(10002,"验证码获取频率太高，请稍后再试"),
    VAILD_EXCEPTION(10001,"参数校验失败"),
    UNKNOW_EXCEPION(10000,"系统未知异常"),
    OK(0,"SUCCESS"),
    ERROR(500,"ERROR"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常"),
    USER_EXISTS_EXCEPTION(15001,"用户名已存在"),
    PHONE_EXISTS_EXCEPTION(15002,"手机号已存在"),
    LOGINACCT_PASSWORD_EXCEPTION(15003,"账号或密码错误"),
    OAUTH2_LOGIN_EXCEPTION(20001,"登录失败"),
    NO_STOCK_EXCEPTION(21000,"商品库存不足");


    private int code;
    private String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.onlineshopping.auth.vo;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegistVo {
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 18,message = "密码长度必须在6-18之间")
    private String password;
    @Pattern(regexp = "^1[3456789]\\d{9}$",message = "手机号格式错误")
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @NotEmpty(message = "验证码不能为空")
    private String code;
}

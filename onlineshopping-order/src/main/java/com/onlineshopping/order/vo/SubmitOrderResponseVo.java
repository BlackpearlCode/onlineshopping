package com.onlineshopping.order.vo;

import com.onlineshopping.order.entity.Order;
import lombok.Data;

@Data
public class SubmitOrderResponseVo {
    private Order order;
    //错误状态码 0：成功
    private Integer code;
}

package com.onlineshopping.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单提交数据
 *
 */
@Data
public class OrderSubmitVo {
    // 收货地址id
    private Long addrId;
    //支付方式
    private Integer payType;
    //防重令牌
    private String orderToken;
    //应付总额
    private BigDecimal payPrice;
}

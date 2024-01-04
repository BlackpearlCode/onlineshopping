package com.onlineshopping.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退款信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundInfo implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 退款的订单
    */
    private Long orderReturnId;

    /**
    * 退款金额
    */
    private BigDecimal refund;

    /**
    * 退款交易流水号
    */
    private String refundSn;

    /**
    * 退款状态
    */
    private Boolean refundStatus;

    /**
    * 退款渠道[1-支付宝，2-微信，3-银联，4-汇款]
    */
    private Byte refundChannel;

    private String refundContent;

    private static final long serialVersionUID = 1L;
}
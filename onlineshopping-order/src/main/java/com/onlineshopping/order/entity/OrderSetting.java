package com.onlineshopping.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单配置信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSetting implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 秒杀订单超时关闭时间(分)
    */
    private Integer flashOrderOvertime;

    /**
    * 正常订单超时时间(分)
    */
    private Integer normalOrderOvertime;

    /**
    * 发货后自动确认收货时间（天）
    */
    private Integer confirmOvertime;

    /**
    * 自动完成交易时间，不能申请退货（天）
    */
    private Integer finishOvertime;

    /**
    * 订单完成后自动好评时间（天）
    */
    private Integer commentOvertime;

    /**
    * 会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】
    */
    private Byte memberLevel;

    private static final long serialVersionUID = 1L;
}
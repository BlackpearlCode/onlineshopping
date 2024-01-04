package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 优惠券领取历史记录
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponHistory implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 优惠券id
    */
    private Long couponId;

    /**
    * 会员id
    */
    private Long memberId;

    /**
    * 会员名字
    */
    private String memberNickName;

    /**
    * 获取方式[0->后台赠送；1->主动领取]
    */
    private Boolean getType;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 使用状态[0->未使用；1->已使用；2->已过期]
    */
    private Boolean useType;

    /**
    * 使用时间
    */
    private Date useTime;

    /**
    * 订单id
    */
    private Long orderId;

    /**
    * 订单号
    */
    private Long orderSn;

    private static final long serialVersionUID = 1L;
}
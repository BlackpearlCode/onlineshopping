package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
    * 商品spu积分设置
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpuBounds implements Serializable {
    /**
    * id
    */
    private Long id;

    private Long spuId;

    /**
    * 成长积分
    */
    private BigDecimal growBounds;

    /**
    * 购物积分
    */
    private BigDecimal buyBounds;

    /**
    * 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]
    */
    private Boolean work;

    private static final long serialVersionUID = 1L;
}
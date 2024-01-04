package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
    * 商品会员价格
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPrice implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * sku_id
    */
    private Long skuId;

    /**
    * 会员等级id
    */
    private Long memberLevelId;

    /**
    * 会员等级名
    */
    private String memberLevelName;

    /**
    * 会员对应价格
    */
    private BigDecimal memberPrice;

    /**
    * 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
    */
    private Boolean addOther;

    private static final long serialVersionUID = 1L;
}
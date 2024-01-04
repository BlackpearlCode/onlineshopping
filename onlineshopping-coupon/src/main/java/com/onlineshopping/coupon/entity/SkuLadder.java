package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
    * 商品阶梯价格
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuLadder implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * spu_id
    */
    private Long skuId;

    /**
    * 满几件
    */
    private Integer fullCount;

    /**
    * 打几折
    */
    private BigDecimal discount;

    /**
    * 折后价
    */
    private BigDecimal price;

    /**
    * 是否叠加其他优惠[0-不可叠加，1-可叠加]
    */
    private Boolean addOther;

    private static final long serialVersionUID = 1L;
}
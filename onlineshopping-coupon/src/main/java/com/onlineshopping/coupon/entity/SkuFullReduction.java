package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
    * 商品满减信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuFullReduction implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * spu_id
    */
    private Long skuId;

    /**
    * 满多少
    */
    private BigDecimal fullPrice;

    /**
    * 减多少
    */
    private BigDecimal reducePrice;

    /**
    * 是否参与其他优惠
    */
    private Boolean addOther;

    private static final long serialVersionUID = 1L;
}
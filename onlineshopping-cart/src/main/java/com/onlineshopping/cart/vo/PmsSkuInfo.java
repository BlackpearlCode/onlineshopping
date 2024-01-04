package com.onlineshopping.cart.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
    * sku信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsSkuInfo implements Serializable {
    /**
    * skuId
    */
    private Long skuId;

    /**
    * spuId
    */
    private Long spuId;

    /**
    * sku名称
    */
    private String skuName;

    /**
    * sku介绍描述
    */
    private String skuDesc;

    /**
    * 所属分类id
    */
    private Long catalogId;

    /**
    * 品牌id
    */
    private Long brandId;

    /**
    * 默认图片
    */
    private String skuDefaultImg;

    /**
    * 标题
    */
    private String skuTitle;

    /**
    * 副标题
    */
    private String skuSubtitle;

    /**
    * 价格
    */
    private BigDecimal price;

    /**
    * 销量
    */
    private Long saleCount;

    private static final long serialVersionUID = 1L;
}
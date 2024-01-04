package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * sku图片
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsSkuImages implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * sku_id
    */
    private Long skuId;

    /**
    * 图片地址
    */
    private String imgUrl;

    /**
    * 排序
    */
    private Integer imgSort;

    /**
    * 默认图[0 - 不是默认图，1 - 是默认图]
    */
    private Integer defaultImg;

    private static final long serialVersionUID = 1L;
}
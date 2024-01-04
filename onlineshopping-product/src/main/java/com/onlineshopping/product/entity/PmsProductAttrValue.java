package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * spu属性值
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductAttrValue implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 商品id
    */
    private Long spuId;

    /**
    * 属性id
    */
    private Long attrId;

    /**
    * 属性名
    */
    private String attrName;

    /**
    * 属性值
    */
    private String attrValue;

    /**
    * 顺序
    */
    private Integer attrSort;

    /**
    * 快速展示【是否展示在介绍上；0-否 1-是】
    */
    private Byte quickShow;

    private static final long serialVersionUID = 1L;
}
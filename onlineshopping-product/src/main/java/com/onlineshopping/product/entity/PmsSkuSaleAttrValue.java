package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * sku销售属性&值
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsSkuSaleAttrValue implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * sku_id
    */
    private Long skuId;

    /**
    * attr_id
    */
    private Long attrId;

    /**
    * 销售属性名
    */
    private String attrName;

    /**
    * 销售属性值
    */
    private String attrValue;

    /**
    * 顺序
    */
    private Integer attrSort;

    private static final long serialVersionUID = 1L;
}
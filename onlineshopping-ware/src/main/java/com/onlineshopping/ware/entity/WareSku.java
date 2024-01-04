package com.onlineshopping.ware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
    * 商品库存
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WareSku implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * sku_id
    */
    private Long skuId;

    /**
    * 仓库id
    */
    private Long wareId;

    /**
    * 库存数
    */
    private Integer stock;

    /**
    * sku_name
    */
    private String skuName;

    /**
    * 锁定库存
    */
    private Integer stockLocked;

    private static final long serialVersionUID = 1L;
}
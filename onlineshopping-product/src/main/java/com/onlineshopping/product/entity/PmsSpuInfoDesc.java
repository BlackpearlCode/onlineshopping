package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * spu信息介绍
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsSpuInfoDesc implements Serializable {
    /**
    * 商品id
    */
    private Long spuId;

    /**
    * 商品介绍
    */
    private String decript;

    private static final long serialVersionUID = 1L;
}
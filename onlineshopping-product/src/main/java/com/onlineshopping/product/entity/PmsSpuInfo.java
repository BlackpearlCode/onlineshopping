package com.onlineshopping.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * spu信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsSpuInfo implements Serializable {
    /**
    * 商品id
    */
    private Long id;

    /**
    * 商品名称
    */
    private String spuName;

    /**
    * 商品描述
    */
    private String spuDescription;

    /**
    * 所属分类id
    */
    private Long catalogId;

    /**
    * 品牌id
    */
    private Long brandId;

    private BigDecimal weight;

    /**
    * 上架状态[0 - 下架，1 - 上架]
    */
    private Integer publishStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
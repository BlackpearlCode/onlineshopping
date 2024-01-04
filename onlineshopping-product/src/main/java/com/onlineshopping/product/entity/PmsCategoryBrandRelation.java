package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 品牌分类关联
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsCategoryBrandRelation implements Serializable {
    private Long id;

    /**
    * 品牌id
    */
    private Long brandId;

    /**
    * 分类id
    */
    private Long catelogId;

    private String brandName;

    private String catelogName;

    private static final long serialVersionUID = 1L;
}
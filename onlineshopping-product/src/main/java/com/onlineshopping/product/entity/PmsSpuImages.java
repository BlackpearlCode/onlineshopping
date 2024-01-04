package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * spu图片
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsSpuImages implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * spu_id
    */
    private Long spuId;

    /**
    * 图片名
    */
    private String imgName;

    /**
    * 图片地址
    */
    private String imgUrl;

    /**
    * 顺序
    */
    private Integer imgSort;

    /**
    * 是否默认图
    */
    private Byte defaultImg;

    private static final long serialVersionUID = 1L;
}
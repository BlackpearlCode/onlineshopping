package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
    * 专题商品
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeSubjectSpu implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 专题名字
    */
    private String name;

    /**
    * 专题id
    */
    private Long subjectId;

    /**
    * spu_id
    */
    private Long spuId;

    /**
    * 排序
    */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}
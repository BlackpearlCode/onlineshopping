package com.onlineshopping.product.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
    * 商品三级分类
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsCategory implements Serializable{
    /**
    * 分类id
    */
    private Long catId;

    /**
    * 分类名称
    */
    private String name;

    /**
    * 父分类id
    */
    private Long parentCid;

    /**
    * 层级
    */
    private Integer catLevel;

    /**
    * 是否显示[0-不显示，1显示]
    */
    private Byte showStatus;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 图标地址
    */
    private String icon;

    /**
    * 计量单位
    */
    private String productUnit;

    /**
    * 商品数量
    */
    private Integer productCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)//这个字段不是null才序列化，如果为空则不序列化传给前端
    private List<PmsCategory> children;

    private static final long serialVersionUID = 1L;

}
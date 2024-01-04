package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 属性分组
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsAttrGroup implements Serializable {
    /**
    * 分组id
    */
    private Long attrGroupId;

    /**
    * 组名
    */
    private String attrGroupName;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 描述
    */
    private String descript;

    /**
    * 组图标
    */
    private String icon;

    /**
    * 所属分类id
    */
    private Long catelogId;

    /**
     * 三级分类完整路径
     */
    private Long[] catelogPath;

    private static final long serialVersionUID = 1L;
}
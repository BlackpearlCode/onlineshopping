package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 属性&属性分组关联
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsAttrAttrgroupRelation implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 属性id
    */
    private Long attrId;

    /**
    * 属性分组id
    */
    private Long attrGroupId;

    /**
    * 属性组内排序
    */
    private Integer attrSort;

    private static final long serialVersionUID = 1L;
}
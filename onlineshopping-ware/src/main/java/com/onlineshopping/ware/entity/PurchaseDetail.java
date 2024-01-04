package com.onlineshopping.ware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetail implements Serializable {
    private Long id;

    /**
    * 采购单id
    */
    private Long purchaseId;

    /**
    * 采购商品id
    */
    private Long skuId;

    /**
    * 采购数量
    */
    private Integer skuNum;

    /**
    * 采购金额
    */
    private BigDecimal skuPrice;

    /**
    * 仓库id
    */
    private Long wareId;

    /**
    * 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
    */
    private Integer status;

    private static final long serialVersionUID = 1L;
}
package com.onlineshopping.ware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
    * 库存工作单
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WareOrderTaskDetail implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * sku_id
    */
    private Long skuId;

    /**
    * sku_name
    */
    private String skuName;

    /**
    * 购买个数
    */
    private Integer skuNum;

    /**
    * 工作单id
    */
    private Long taskId;

    /**
    * 仓库id
    */
    private Long wareId;

    /**
    * 1-已锁定  2-已解锁  3-扣减
    */
    private Integer lockStatus;

    private static final long serialVersionUID = 1L;
}
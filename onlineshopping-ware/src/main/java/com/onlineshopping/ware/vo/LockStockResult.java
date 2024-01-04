package com.onlineshopping.ware.vo;

import lombok.Data;

/**
 * 库存锁定结果
 */
@Data
public class LockStockResult {
    // 商品id
    private Long skuId;
    //商品锁了几件
    private Integer num;
    //商品是否被锁定成功
    private Boolean locked;
}

package com.onlineshopping.ware.exception;

import lombok.Data;

@Data
public class NoStockException extends RuntimeException{
    private Long skuId;
    public NoStockException(Long skuId) {
        super("商品id:" + skuId + "没有足够的库存");
    }
}

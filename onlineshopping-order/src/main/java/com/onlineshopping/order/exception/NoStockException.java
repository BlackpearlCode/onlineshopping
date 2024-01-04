package com.onlineshopping.order.exception;

import lombok.Data;

@Data
public class NoStockException extends RuntimeException{
    private Long skuId;
    public NoStockException(Long skuId) {
        super("商品id:" + skuId + "没有足够的库存");
    }

    public NoStockException() {
        super("没有足够的库存");
    }
}

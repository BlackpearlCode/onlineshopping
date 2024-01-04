package com.onlineshopping.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuInfoVo {
    private Long skuId;
    private String skuName;
    private String skuDefaultImg;
    private BigDecimal price;
    private Long saleCount;
    private String skuDesc;
    private Long catalogId;
    private Long brandId;
    private String skuTitle;
    private String skuSubtitle;
    private Long spuId;
}

package com.onlineshopping.ware.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemVo {
    //商品id
    private Long skuId;

    //商品标题
    private String title;
    //商品图片
    private String image;
    //商品套餐信息
    private List<String> skuAttr;
    //商品价格
    private BigDecimal price;
    //商品数量
    private Integer count;
    //商品总价
    private BigDecimal totalPrice;
    //是否有库存
    private boolean hasStock;
    //商品重量
    private BigDecimal weight;
}


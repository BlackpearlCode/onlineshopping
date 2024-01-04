package com.onlineshopping.common.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuEsModel {

    private Long skuId;
    private Long spuId;
    //商品标题
    private String skuTitle;
    //商品售价
    private BigDecimal skuPrice;
    //商品图片
    private String skuImg;
    //商品销量
    private Long saleCount;
    //是否拥有库存
    private Boolean hasStock;
    //商品热度评分
    private Long hotScore;
    //商品品牌Id
    private Long brandId;
    //商品分类Id
    private Long catalogId;
    //商品品牌名
    private String brandName;
    //商品品牌logo
    private String brandImg;
    //商品分类名称
    private String catalogName;
    //商品规格属性信息
    private List<Attrs> attrs;

    @Data
    public static class Attrs{
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}

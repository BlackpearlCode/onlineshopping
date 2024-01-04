package com.onlineshopping.es.vo;

import lombok.Data;

import java.util.List;

/**
 * 封装页面所有可能传递过来的查询条件
 */
@Data
public class SearchParam {
    private String keyword;//页面传递过来的全文匹配关键字
    private Long catalog3Id;//三级分类id
    private String sort;//排序条件（排序条件：商品热度、销量、价格）
    /**
     *过滤条件
     */
    private Integer hasStock=1;//是否只显示有货（0：无货；1：有货）
    /**
     * 价格区间用“_”连接；
     * 例如：0_50:价格区间为0到50
     *      _50：价格区间为小于等于50
     *      50_：价格区间为大于50
     */
    private String skuPrice;//价格区间
    private List<Long> brandId;//按照品牌id查询（可多选）
    private List<String> attrs;//按照属性进行筛选
    private Integer pageNum=1;//页码

    private String _queryString;//原生的所有查询条件

}

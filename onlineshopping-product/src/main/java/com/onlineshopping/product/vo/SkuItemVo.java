package com.onlineshopping.product.vo;

import com.onlineshopping.product.entity.PmsSkuImages;
import com.onlineshopping.product.entity.PmsSkuInfo;
import com.onlineshopping.product.entity.PmsSpuInfoDesc;
import lombok.Data;

import java.util.List;
@Data
public class SkuItemVo {

    //sku基本信息
    private PmsSkuInfo info;
    //是否有货；true：有货；false：无货
    boolean hasStock=true;
    //sku图片信息
    private List<PmsSkuImages> images;
    //获取spu的销售属性组合
    private List<SkuItemSaleAttrVo> saleAttr;
    //获取spu的介绍
    private PmsSpuInfoDesc desc;
    //获取spu的规格参数信息
    private List<SpuItemAttrGroupVo>   groupAttrs;
    //秒杀信息
    private SeckillInfoVo seckillInfo;

}

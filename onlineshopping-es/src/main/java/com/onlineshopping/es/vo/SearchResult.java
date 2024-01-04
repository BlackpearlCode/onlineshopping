package com.onlineshopping.es.vo;

import com.onlineshopping.common.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {
    //查询到的所有商品信息
    private List<SkuEsModel> products;
    private Integer pageNum;//当前页
    private Long total;//总记录数
    private Integer totalPages;//总页码
    private List<Integer>pageNavs;//导航页码
    private List<BrandVo> brands;//当前查询到的结果，所有涉及到的品牌
    private List<AttrVo> attrs;//当前查询到的结果，所有涉及到的属性
    private List<CatalogVo> catalogs;//当前查询到的结果，所有涉及到的分类
    //==============以上是返回给页面的所有信息=========================
    
    //面包屑导航
    private List<NavNo> navs=new ArrayList<>();
    private List<Long> attrIds=new ArrayList<>();
    @Data
    public static class NavNo{
        //导航名
        private String navName;
        //导航值
        private String navValue;
        //跳转位置
        private String link;
    }
    @Data
    public static class BrandVo{
        //品牌id
        private Long brandId;
        //商品品牌名
        private String brandName;
        //商品品牌logo
        private String brandImg;
    }

    @Data
    public static class AttrVo{
        //属性id
        private Long attrId;
        //属性名
        private String attrName;
        //属性值
        private List<String> attrValue;
    }

    @Data
    public static class CatalogVo{
        //商品分类Id
        private Long catalogId;
        //商品分类名称
        private String catalogName;
    }

}

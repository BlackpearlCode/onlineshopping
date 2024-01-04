package com.onlineshopping.es.service.serviceImpl;
import com.google.gson.reflect.TypeToken;
import com.onlineshopping.es.constant.EsConstant;
import com.onlineshopping.es.feign.ProductFeign;
import com.onlineshopping.es.service.IMallSearchService;
import com.onlineshopping.es.vo.AttrResponseVo;
import com.onlineshopping.es.vo.BrandVo;
import com.onlineshopping.es.vo.SearchParam;
import com.onlineshopping.es.vo.SearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.onlineshopping.common.es.SkuEsModel;
import com.onlineshopping.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MallSearchServiceImpl implements IMallSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ProductFeign productFeign;


    @Override
    public SearchResult search(SearchParam param) throws IOException {
        //动态构建查询选哟的DSL语句
        SearchResult result;
        //准备检索请求
        SearchRequest searchRequest = buildSearchRequrest(param);
        //执行检索请求
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        //分析响应数据封装成需要的格式
        result=buildSearchResult(search,param);

        log.info("response:{}",search);
        return result;
    }





        /**
         * 准备检索请求
         */
    private SearchRequest buildSearchRequrest(SearchParam param) {
        //构建DSL语句
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        /**
         * 查询：过滤（按照属性、分类、品牌、价格区间、库存）
         */
        //构建boolQuery
        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        //must模糊匹配
        if (!StringUtils.isEmpty(param.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
        //按照三级分类id查询
        if (param.getCatalog3Id() != null) {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
        //根据品牌id查询
        if (!CollectionUtils.isEmpty(param.getBrandId())) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
        }
        //按照属性查询
        if (!CollectionUtils.isEmpty(param.getAttrs())) {

            for (String attrStr : param.getAttrs()) {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                String[] s = attrStr.split("_");
                //检索属性id
                String attrId = s[0];
                //检索属性id对应的值
                String[] attrValue = s[1].split(":");
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValue));
                //创建neste查询语句
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }

        }
        //按照有库存进行查询(0：无库存；1：有库存)
        boolQuery.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 1));

        //按照价格区间进行检索
        if (!StringUtils.isEmpty(param.getSkuPrice())) {
            /**
             * 价格区间用“_”连接；
             * 例如：0_50:价格区间为0到50
             *      _50：价格区间为小于等于50
             *      50_：价格区间为大于50
             */
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            String[] priceArray = param.getSkuPrice().split("_");
            if (priceArray.length == 2) {
                rangeQuery.gte(priceArray[0]).lte(priceArray[1]);
            } else if (priceArray.length == 1) {
                if (param.getSkuPrice().startsWith("_")) {
                    rangeQuery.lte(priceArray[0]);
                }

                if (param.getSkuPrice().endsWith("_")) {
                    rangeQuery.gte(priceArray[0]);
                }
            }
            boolQuery.filter(rangeQuery);
        }

        searchSourceBuilder.query(boolQuery);

        /**
         * 排序：分页、高亮
         */

        //排序
        if (!StringUtils.isEmpty(param.getSort())) {
            String sort = param.getSort();
            /**
             * 例如：sort=hotScore_asc/desc
             * "_"前是排序字段
             * "_"后升序还是降序
             */
            String[] s = sort.split("_");
            String sortKey = s[0];
            SortOrder order = s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            searchSourceBuilder.sort(sortKey, order);
        }

        //分页
        searchSourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        //高亮
        if (!StringUtils.isEmpty(param.getKeyword())) {
            HighlightBuilder builder = new HighlightBuilder();
            builder.field("skuTitle");
            builder.preTags("<b style='color:red'>");
            builder.postTags("</b>");
            searchSourceBuilder.highlighter(builder);
        }

        /**
         * 聚合分析
         */
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        //品牌聚合
        brand_agg.field("brandId").size(50);
        //品牌聚合的子聚合
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        //聚合品牌信息
        searchSourceBuilder.aggregation(brand_agg);
        //分类聚合
        TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        //聚合分类信息
        searchSourceBuilder.aggregation(catalog_agg);

        //属性聚合
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        //聚合出当前所有的attrid
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
        //聚合出当前attrid对应的名字
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        //聚合分析出当前attr_id对应的所有可能的属性值
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        //聚合属性信息
        attr_agg.subAggregation(attr_id_agg);
        searchSourceBuilder.aggregation(attr_agg);



        log.info("构建的的DSL:{}", searchSourceBuilder.toString());// Create the Builder object


        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);

        return searchRequest;
    }

    /**
     * 构建结果数据
     * @param response
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse response,SearchParam param) throws JsonProcessingException {
        SearchResult result = new SearchResult();
        SearchHits hits = response.getHits();
        List<SkuEsModel> skuEsModelList=new ArrayList<>();
        if(hits.getHits()!=null && hits.getHits().length>0){
            for (SearchHit hit : hits.getHits()) {
                String sourceString = hit.getSourceAsString();
                Gson gson=new Gson();
                SkuEsModel skuEsModel = gson.fromJson(sourceString, SkuEsModel.class);
                if(!StringUtils.isEmpty(param.getKeyword())){
                    HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                    String string = skuTitle.getFragments()[0].toString();
                    skuEsModel.setSkuTitle(string);
                }
                skuEsModelList.add(skuEsModel);
            }
        }


        List<SearchResult.CatalogVo> catalogVos=new ArrayList<>();
        //获取当前商品获取到的分类信息
        ParsedLongTerms catalogAgg = response.getAggregations().get("catalog_agg");
        for (Terms.Bucket bucket : catalogAgg.getBuckets()) {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            //得到分类Id
            catalogVo.setCatalogId(Long.valueOf(bucket.getKeyAsString()));
            //获取分类名
            ParsedStringTerms catalog_name_agg = bucket.getAggregations().get("catalog_name_agg");
            String keyString = catalog_name_agg.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(keyString);
            catalogVos.add(catalogVo);
        }
        List<SearchResult.BrandVo> brandVos=new ArrayList<>();
        //获取当前品牌信息
        ParsedLongTerms brandAgg = response.getAggregations().get("brand_agg");
        for (Terms.Bucket bucket : brandAgg.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            long brandId = bucket.getKeyAsNumber().longValue();
            ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brand_img_agg");
            String brandImg = brandImgAgg.getBuckets().get(0).getKeyAsString();
            ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brand_name_agg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandId(brandId);
            brandVo.setBrandName(brandName);
            brandVo.setBrandImg(brandImg);
            brandVos.add(brandVo);
        }
        //获取当前属性信息
        List<SearchResult.AttrVo> attrVos=new ArrayList<>();
        ParsedNested attrAgg = response.getAggregations().get("attr_agg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
            long attrId = bucket.getKeyAsNumber().longValue();
            ParsedStringTerms attrNameAgg=bucket.getAggregations().get("attr_name_agg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            ParsedStringTerms attrValueAgg=bucket.getAggregations().get("attr_value_agg");
            List<String> attrValues = attrValueAgg.getBuckets().stream().map(item -> {
                String keyAsString = item.getKeyAsString();
                return keyAsString;
            }).collect(Collectors.toList());
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            attrVo.setAttrId(attrId);
            attrVo.setAttrName(attrName);
            attrVo.setAttrValue(attrValues);
            attrVos.add(attrVo);
        }
        result.setBrands(brandVos);
        long total=hits.getTotalHits().value;
        //设置总记录数
        result.setTotal(total);
        //计算总页码
        int totalPage =(int) (total % EsConstant.PRODUCT_PAGESIZE == 0 ? total / EsConstant.PRODUCT_PAGESIZE : (total / EsConstant.PRODUCT_PAGESIZE + 1));
        result.setTotalPages(totalPage);
        //当前页
        int pageNum=param.getPageNum();
        result.setPageNum(pageNum);
        //设置商品信息
        result.setProducts(skuEsModelList);

        //设置商品属性
        result.setAttrs(attrVos);

        //设置商品分类
        result.setCatalogs(catalogVos);

        List<Integer> pageNavs=new ArrayList<>();
        for(int i=1;i<=totalPage;i++){
            pageNavs.add(i);
        }
        result.setPageNavs(pageNavs);
        //构建面包屑导航
        if (param.getAttrs() != null && param.getAttrs().size() > 0) {
            List<SearchResult.NavNo> collect = param.getAttrs().stream().map(attr -> {
                //1、分析每一个attrs传过来的参数值
                SearchResult.NavNo navVo = new SearchResult.NavNo();
                String[] s = attr.split("_");
                navVo.setNavValue(s[1]);
                Result r = productFeign.info(Long.parseLong(s[0]));
                result.getAttrIds().add(Long.parseLong(s[0]));
                if (r.getCode() == 0) {
                    Gson gson = new Gson();
                    Object attrInfo = r.get("attr");
                    String json = gson.toJson(attrInfo);
                    AttrResponseVo data=gson.fromJson(json, AttrResponseVo.class);
                    navVo.setNavName(data.getAttrName());
                } else {
                    navVo.setNavName(s[0]);
                }

                //2、取消了这个面包屑以后，我们要跳转到哪个地方，将请求的地址url里面的当前置空
                //拿到所有的查询条件，去掉当前
                String replace = replaceQueryString(param, attr,"attrs");
                navVo.setLink("http://search.onlineshopping.com/list.html?" + replace);

                return navVo;
            }).collect(Collectors.toList());

            result.setNavs(collect);
        }
        //品牌，分类
        if(!CollectionUtils.isEmpty(param.getBrandId())){
            List<SearchResult.NavNo> navs = result.getNavs();
            SearchResult.NavNo navNo = new SearchResult.NavNo();
            navNo.setNavName("品牌");
            //TODO 远程查询所有品牌
            Result r = productFeign.brandInfoByIds(param.getBrandId());
            if(r.getCode()==0){
                Gson gson = new Gson();
                Object brand = r.get("brand");
                String json = gson.toJson(brand);
                List<BrandVo> brandVoList = gson.fromJson(json,new TypeToken<List<BrandVo>>() {}.getType());
                StringBuffer buffer = new StringBuffer();
                String replace="";
                for (BrandVo brandVo : brandVoList) {
                    buffer.append(brandVo.getName()+";");
                    replace=replaceQueryString( param,  brandVo.getBrandId()+"", "brandId");
                }
                navNo.setNavValue(buffer.toString());
                navNo.setLink("http://search.onlineshopping.com/list.html?" + replace);
            }
            navs.add(navNo);
        }


        return result;
    }

    private static String replaceQueryString(SearchParam param, String value,String key) {
        String encode = null;
        try {
            encode = URLEncoder.encode(value,"UTF-8");
            encode.replace("+","%20");  //浏览器对空格的编码和Java不一样，差异化处理
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str= param.get_queryString().replace("(","%28").replace(")","%29");
        String replace = str.replace("&"+key+"=" + encode, "");
        return replace;
    }
}

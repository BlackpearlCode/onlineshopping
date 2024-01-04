package com.onlineshopping.product.service.impl;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onlineshopping.common.constant.ProductConstant;
import com.onlineshopping.common.es.SkuEsModel;
import com.onlineshopping.common.to.SpuBoundTo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.constant.PublishStatusConstrant;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.product.entity.*;
import com.onlineshopping.product.feign.CouponFeignService;
import com.onlineshopping.product.feign.SearchFeignService;
import com.onlineshopping.product.feign.WareFeignService;
import com.onlineshopping.product.mapper.PmsSpuInfoMapper;
import com.onlineshopping.product.service.*;
import com.onlineshopping.product.vo.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PmsSpuInfoServiceImpl implements PmsSpuInfoService {

    @Resource
    private PmsSpuInfoMapper pmsSpuInfoMapper;

    @Resource
    private PmsSpuInfoDescService pmsSpuInfoDescService;

    @Resource
    private PmsSpuImagesService pmsSpuImagesService;

    @Resource
    private PmsProductAttrValueService productAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Resource
    private PmsSkuInfoService pmsSkuInfoService;

    @Resource
    private PmsBrandService brandService;

    @Resource
    private PmsCategoryService categoryService;

    @Resource
    private PmsAttrService attrService;

    @Resource
    private WareFeignService wareFeignService;

    @Resource
    private SearchFeignService searchFeignService;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return pmsSpuInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PmsSpuInfo record) {
        return pmsSpuInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsSpuInfo record) {
        return pmsSpuInfoMapper.insertSelective(record);
    }

    @Override
    public PmsSpuInfo selectByPrimaryKey(Long id) {
        return pmsSpuInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsSpuInfo record) {
        return pmsSpuInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsSpuInfo record) {
        return pmsSpuInfoMapper.updateByPrimaryKey(record);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuInFo spu) {
        //1.保存spu基本信息 pms_spu_info
        PmsSpuInfo spuInfo=new PmsSpuInfo();
        BeanUtils.copyProperties(spu,spuInfo);
        spuInfo.setCreateTime(new Date());
        spuInfo.setUpdateTime(new Date());
        spuInfo.setPublishStatus(PublishStatusConstrant.NEWbUILE.getCode());
        pmsSpuInfoMapper.insert(spuInfo);
        //2.保存spu的描述图片 pms_spu_info_desc
        PmsSpuInfoDesc spuInfoDesc=new PmsSpuInfoDesc();
        spuInfoDesc.setSpuId(spuInfo.getId());
        spuInfoDesc.setDecript(String.join(",",spu.getDecript()));
        pmsSpuInfoDescService.insert(spuInfoDesc);
        //3.保存spu的图片集 pms_spu_images
        List<String> images = spu.getImages();
        Long id=spuInfo.getId();
        pmsSpuImagesService.batchSave(id,images);
        //4.保存spu的规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = spu.getBaseAttrs();
        productAttrValueService.save(id,baseAttrs);
        //5.保存spu的积分信息 onlineshopping_sms->sms_spu_bounds
        Bounds bounds = spu.getBounds();
        SpuBoundTo boundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,boundTo);
        boundTo.setSpuId(id);
        couponFeignService.saveSpuBounds(boundTo);
        //6保存当前spu对应的sku信息
        List<Skus> skus = spu.getSkus();
        pmsSkuInfoService.save(id,skus,spuInfo);

    }

    @Override
    public PageEntity getAll(Map<String, Object> params) {

        List<PmsSpuInfo> list=pmsSpuInfoMapper.getAll(params);
        PageHelper.startPage( Integer.parseInt((String) params.get("page")),Integer.parseInt((String) params.get("limit")));
        PageInfo<PmsSpuInfo> spuInFoPageInfo = new PageInfo<>(list);
        PageEntity pageEntity=new PageEntity(spuInFoPageInfo.getTotal(),spuInFoPageInfo.getPages(),spuInFoPageInfo.getList());
        return pageEntity;
    }

    @Override
    public void up(Long id) {

        //根据当前spluId查询出对应的所有sku信息。
        List<PmsSkuInfo> skuInfos = pmsSkuInfoService.selectBySpuId(id);
        List<Long> skuIds = skuInfos.stream().map(sku -> {
            return sku.getSkuId();
        }).collect(Collectors.toList());
        //TODO 3.查询品牌和分类的名字信息
        PmsBrand brand = brandService.selectByPrimaryKey(skuInfos.get(0).getBrandId());
        PmsCategory category = categoryService.selectByPrimaryKey(skuInfos.get(0).getCatalogId());
        //TODO 4.查询当前spu的所有可以被用来检索的规格属性
        //查询可以被检索的规格属性
        Set<Long> attrIds = attrService.selectBySearchType(1L);

        List<SkuEsModel.Attrs> attrList = productAttrValueService.selectBySpuId(id).stream().filter(item -> {
            return attrIds.contains(item.getAttrId());
        }).map(item->{
            SkuEsModel.Attrs esAttr = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item,esAttr);
            return esAttr;
        }).collect(Collectors.toList());
        //TODO 1.发送远程调用，查询是否有库存
        Map<Long, Boolean> stockMap=null;
        try{
            Result r = wareFeignService.getSkusHasStock(skuIds);
            Gson gson = new Gson();
            String data = gson.toJson(r.get("data"));
            List<SkusHasStockVo> list=gson.fromJson(data,new TypeToken<List<SkusHasStockVo>>(){}.getType());
            stockMap= list.stream().collect(
                    Collectors.toMap(SkusHasStockVo::getSkuId, item -> item.getHasStock())
            );
        }catch (Exception e){
            log.error("库存服务查询异常:原因{}",e);
        }

        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> esModels = skuInfos.stream().map(sku -> {
            SkuEsModel esModel = new SkuEsModel();
            BeanUtils.copyProperties(sku,esModel);
            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());
            //设置库存信息
            if(finalStockMap ==null){
                esModel.setHasStock(true);
            }else{
                esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }

            //TODO 2.热度评分
            esModel.setHotScore(0L);
            esModel.setBrandName(brand.getName());
            esModel.setBrandImg(brand.getLogo());
            esModel.setCatalogName(category.getName());

            esModel.setAttrs(attrList);
            return esModel;
        }).collect(Collectors.toList());

        //TODO 5.发送给es进行保存
        Result r=searchFeignService.productStatusUp(esModels);
        if(r.get("code").equals(0)){
            //TODO 6.远程调用成功，修改当前spu的状态
            PmsSpuInfo spu=new PmsSpuInfo();
            spu.setId(id);
            spu.setPublishStatus(ProductConstant.StatusEnum.SPU_UP.getCode());
            spu.setUpdateTime(new Date());
            pmsSpuInfoMapper.updateByPrimaryKeySelective(spu);
        }else{
            //远程调用失败
        }
    }

    @Override
    public PmsSpuInfo getSpuInfoBySkuId(Long skuId) {
        //根据id获取spuId
        PmsSkuInfo skuInfo=pmsSkuInfoService.getSkuInfoBySkuId(skuId);
        PmsSpuInfo spuInfo=pmsSpuInfoMapper.selectByPrimaryKey(skuInfo.getSpuId());
        return spuInfo;
    }
}

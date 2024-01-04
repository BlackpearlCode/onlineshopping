package com.onlineshopping.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.common.to.MemberPriceTo;
import com.onlineshopping.common.to.SkuReductionTo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.*;
import com.onlineshopping.product.feign.CouponFeignService;
import com.onlineshopping.product.mapper.PmsSkuImagesMapper;
import com.onlineshopping.product.mapper.PmsSkuInfoMapper;
import com.onlineshopping.product.mapper.PmsSkuSaleAttrValueMapper;
import com.onlineshopping.product.service.PmsAttrService;
import com.onlineshopping.product.service.PmsSkuInfoService;
import com.onlineshopping.product.service.PmsSkuSaleAttrValueService;
import com.onlineshopping.product.service.PmsSpuInfoDescService;
import com.onlineshopping.product.vo.*;
import com.mysql.cj.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class PmsSkuInfoServiceImpl implements PmsSkuInfoService {

    @Resource
    private PmsSkuInfoMapper pmsSkuInfoMapper;

    @Resource
    private PmsSkuImagesMapper pmsSkuImagesMapper;

    @Resource
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private PmsSpuInfoDescService spuInfoDescService;

    @Autowired
    private PmsAttrService attrService;

    @Autowired
    private PmsSkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public int deleteByPrimaryKey(Long skuId) {
        return pmsSkuInfoMapper.deleteByPrimaryKey(skuId);
    }

    @Override
    public int insert(PmsSkuInfo record) {
        return pmsSkuInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsSkuInfo record) {
        return pmsSkuInfoMapper.insertSelective(record);
    }

    @Override
    public PmsSkuInfo selectByPrimaryKey(Long skuId) {
        return pmsSkuInfoMapper.selectByPrimaryKey(skuId);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsSkuInfo record) {
        return pmsSkuInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsSkuInfo record) {
        return pmsSkuInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public void save(Long id, List<Skus> skus, PmsSpuInfo spuInfo) {
        skus.forEach(sku -> {
            //保存sku的基本信息
            PmsSkuInfo skuInfo = new PmsSkuInfo();
            skus.forEach(item->{
                String defaultImg="";
                //判断是否是默认图片；1：是；0：不是
                for (Images image:item.getImages()) {
                    if(image.getDefaultImg()==1){
                        defaultImg=image.getImgUrl();
                    }
                }
                skuInfo.setSkuDefaultImg(defaultImg);
            });
            BeanUtils.copyProperties(sku, skuInfo);
            skuInfo.setSpuId(id);
            skuInfo.setBrandId(spuInfo.getBrandId());
            skuInfo.setCatalogId(spuInfo.getCatalogId());
            skuInfo.setSaleCount(0L);
            pmsSkuInfoMapper.insert(skuInfo);

            //保存sku的图片信息
            List<PmsSkuImages> imagesList = sku.getImages().stream().map(img -> {
                PmsSkuImages images = new PmsSkuImages();
                images.setSkuId(skuInfo.getSkuId());
                images.setDefaultImg(img.getDefaultImg());
                images.setImgUrl(img.getImgUrl());
                return images;
                //没有图片路径的无需保存
            }).filter(entity->{
                //返回true，需要保存；返回false，不需要保存
                return !StringUtils.isNullOrEmpty(entity.getImgUrl());
            }).collect(Collectors.toList());

            if(imagesList.size()>0){
                pmsSkuImagesMapper.batchSave(imagesList);
            }


            //sku的销售属性信息
            List<Attr> attr = sku.getAttr();
            List<PmsSkuSaleAttrValue> saleAttrList = attr.stream().map(a -> {
                PmsSkuSaleAttrValue saleAttrValue = new PmsSkuSaleAttrValue();
                BeanUtils.copyProperties(a, saleAttrValue);
                saleAttrValue.setSkuId(skuInfo.getSkuId());
                return saleAttrValue;
            }).collect(Collectors.toList());
            pmsSkuSaleAttrValueMapper.batchSave(saleAttrList);

            //7保存sku的优惠、满减信息
            SkuReductionTo skuReductionTo = new SkuReductionTo();
            skuReductionTo.setDiscount(sku.getDiscount());
            skuReductionTo.setCountStatus(sku.getCountStatus());
            skuReductionTo.setFullCount(sku.getFullCount());
            skuReductionTo.setFullPrice(sku.getFullPrice());
            List<MemberPriceTo> memberPriceTos=sku.getMemberPrice().stream().map(item->{
                MemberPriceTo memberPriceTo=new MemberPriceTo();
                BeanUtils.copyProperties(item,memberPriceTo);
                return memberPriceTo;
            }).collect(Collectors.toList());
            skuReductionTo.setMemberPrice(memberPriceTos);
            skuReductionTo.setPriceStatus(sku.getPriceStatus());
            skuReductionTo.setReducePrice(sku.getReducePrice());
            skuReductionTo.setSkuId(skuInfo.getSkuId());
            if(skuReductionTo.getFullCount()>0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal(0))==1){
                couponFeignService.saveSkuReduction(skuReductionTo);
            }

        });

    }

    @Override
    public PageEntity getSkuInfo(Map<String, Object> params) {

        List<PmsSkuInfo> skuList=pmsSkuInfoMapper.getInfo(params);
        List<SkuInfoVo> skuInfoVoList=skuList.stream().map(item->{
            SkuInfoVo vo =new SkuInfoVo();
            BeanUtils.copyProperties(item,vo);
            return vo;
        }).collect(Collectors.toList());

        PageHelper.startPage(Integer.parseInt((String) params.get("page")) ,Integer.parseInt((String) params.get("limit")));
        PageInfo<SkuInfoVo> skuPage = new PageInfo<>(skuInfoVoList);
        PageEntity pageEntity=new PageEntity(skuPage.getTotal(),skuPage.getPages(),skuPage.getList());
        return pageEntity;
    }

    @Override
    public List<PmsSkuInfo> selectBySpuId(Long spuId) {
        List<PmsSkuInfo> skuInfos = pmsSkuInfoMapper.selectBySpuId(spuId);
        return skuInfos;
    }

    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo item=new SkuItemVo();

        CompletableFuture<PmsSkuInfo> infoFuture = CompletableFuture.supplyAsync(() -> {
            //获取sku基本信息
            PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);
            item.setInfo(pmsSkuInfo);
            return pmsSkuInfo;
        }, executor);
        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //获取spu的销售组合
            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(res.getSpuId());
            item.setSaleAttr(saleAttrVos);
        }, executor);

        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
            //获取spu的介绍
            PmsSpuInfoDesc pmsSpuInfoDesc = spuInfoDescService.selectByPrimaryKey(res.getSpuId());
            item.setDesc(pmsSpuInfoDesc);
        }, executor);

        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //获取spu的规格参数信息
            Long catalogId = res.getCatalogId();
            List<SpuItemAttrGroupVo> attrGroupVos = attrService.getAttrGroupWithAttrsBySpuId(catalogId, res.getSpuId());
            item.setGroupAttrs(attrGroupVos);
        }, executor);

        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            //获取sku图片信息
            List<PmsSkuImages> images = pmsSkuImagesMapper.selectBySkuId(skuId);
            item.setImages(images);
        }, executor);

        //等待所有任务都完成
        CompletableFuture.allOf(infoFuture,saleAttrFuture,descFuture,baseAttrFuture,imageFuture).get();


        return item;
    }

    @Override
    public List<String> getSkuSaleAtttrValues(Long skuId) {
        return skuSaleAttrValueService.getSkuSaleAttrValuesAsStringList(skuId);
    }

    @Override
    public PmsSkuInfo getSkuInfoBySkuId(Long skuId) {
        return pmsSkuInfoMapper.getSkuInfoBySkuId(skuId);
    }


}

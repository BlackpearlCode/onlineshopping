package com.onlineshopping.product.controller;

import com.onlineshopping.product.entity.PmsBrand;
import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.valid.AddGroup;
import com.onlineshopping.common.valid.UpdateGroup;
import com.onlineshopping.common.valid.UpdateStatusGroup;
import com.onlineshopping.product.feign.OssFileFeignService;
import com.onlineshopping.common.utils.Result;
import com.github.pagehelper.util.StringUtil;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.service.impl.PmsBrandServiceImpl;
import com.onlineshopping.product.service.impl.PmsCategoryBrandRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("product/brand")
@Validated
public class BrandController {

    @Autowired
    private OssFileFeignService fileService;

    @Autowired
    private PmsBrandServiceImpl brandService;

    @Autowired
    private PmsCategoryBrandRelationServiceImpl brandRelationService;

    @RequestMapping("/list")
    public Result selectAll(String page,String limit){
        PageEntity pageEntity= brandService.selectAll(Integer.parseInt(page), Integer.parseInt(limit));
        return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    //新增品牌
    @RequestMapping("/save")
    public Result save(@RequestBody @Validated(AddGroup.class) PmsBrand brand){
        brandService.insert(brand);
        return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());
    }

    //单个 or 批量删除品牌
    @RequestMapping("/delete")
    public Result delete(@RequestBody List<Long> ids){
        //删除关联表中的数据
        brandRelationService.deleteByBrandId(ids);
        if(ids.size()>1){
            List ossPathList=new LinkedList();
            for (Long brandId:ids) {
                PmsBrand pmsBrand = brandService.selectByPrimaryKey(brandId);
                String ossPath=pmsBrand.getLogo();
                ossPath=ossPath.substring(50);
                ossPathList.add(ossPath);
            }
            fileService.batchDelete(ossPathList);
            brandService.batchDelete(ids);
        }else{
            brandService.deleteByPrimaryKey(Long.valueOf(ids.get(0)));
        }

        return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());
    }



    //通过品牌名条件查询
    @RequestMapping("/selectByName")
    public Result selectByName(String page,String limit,String name){
        PageEntity pageEntity;
        if(StringUtil.isNotEmpty(name)){
            pageEntity= brandService.selectByName(Integer.parseInt(page), Integer.parseInt(limit),name);
        }else{
            pageEntity=brandService.selectAll(Integer.parseInt(page), Integer.parseInt(limit));
        }

        return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    //通过品牌Id查询品牌信息
    @RequestMapping("/infos/{brandId}")
    public Result brandInfoById(@PathVariable("brandId") String id){
        PmsBrand pmsBrand = brandService.selectByPrimaryKey(Long.valueOf(id));
        return  Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg()).put("brand",pmsBrand);

    }


    //查询品牌信息
    @RequestMapping("/infos")
    public Result brandInfoByIds(@RequestParam List<Long> brandIds){
        List<PmsBrand> brands=brandService.getBrands(brandIds);
        return  Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg()).put("brand",brands);

    }

    //修改品牌显示状态
    @RequestMapping("/update/status")
    public Result updateShowStatus(@Validated(UpdateStatusGroup.class) @RequestBody PmsBrand pmsBrand){
        PmsBrand brandEntity = brandService.selectByPrimaryKey(Long.valueOf(pmsBrand.getBrandId()));
        brandEntity.setShowStatus(pmsBrand.getShowStatus());
        brandService.updateByPrimaryKey(brandEntity);
        return  Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());
    }

    //根据品牌id修改品牌信息
    @RequestMapping("/update")
    public Result updateBrandInfo(@Validated(UpdateGroup.class) @RequestBody PmsBrand brand){
        PmsBrand pmsBrand = brandService.selectByPrimaryKey(brand.getBrandId());

        if(brand.equals(pmsBrand)){
            return  Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());
        }else{
            String logoPath = pmsBrand.getLogo();
            logoPath=logoPath.substring(50);
            List<String> logoPathList=new LinkedList<>();
            logoPathList.add(logoPath);
            //删除oss对象
            fileService.batchDelete(logoPathList);
            pmsBrand.setShowStatus(brand.getShowStatus());
            if(!pmsBrand.getName().equals(brand.getName())){
                brandRelationService.updateByBrandId(brand.getBrandId(), brand.getName());
            }
            pmsBrand.setName(brand.getName());
            pmsBrand.setLogo(brand.getLogo());
            pmsBrand.setSort(brand.getSort());
            pmsBrand.setDescript(brand.getDescript());
            pmsBrand.setFirstLetter(brand.getFirstLetter());
            brandService.updateByPrimaryKey(pmsBrand);
            return  Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());
        }
    }
}

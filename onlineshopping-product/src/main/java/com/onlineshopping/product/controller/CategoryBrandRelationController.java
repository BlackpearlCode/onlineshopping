package com.onlineshopping.product.controller;


import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.product.entity.PmsCategoryBrandRelation;
import com.onlineshopping.product.service.impl.PmsBrandServiceImpl;
import com.onlineshopping.product.service.impl.PmsCategoryBrandRelationServiceImpl;
import com.onlineshopping.product.service.impl.PmsCategoryServiceImpl;
import com.onlineshopping.product.vo.RalationBrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {

    @Autowired
    private PmsCategoryBrandRelationServiceImpl brandRelationService;
    
    @Autowired
    private PmsCategoryServiceImpl categoryService;
    
    @Autowired
    private PmsBrandServiceImpl brandService;


    /**
     * 获取当前品牌关联的所有分类列表
     * @param brandId
     * @return
     */
    @GetMapping(value="/catelog/list")
    public Result list(@RequestParam("brandId") Long brandId){
        List<PmsCategoryBrandRelation> categoryBrandRelation = brandRelationService.selectByBrandId(brandId);
        return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg()).put("data",categoryBrandRelation);
    }


    @RequestMapping("/save")
    public Result save(@RequestBody PmsCategoryBrandRelation brandRelation){
        String catelogName = categoryService.selectByPrimaryKey(brandRelation.getCatelogId()).getName();
        String brandName = brandService.selectByPrimaryKey(brandRelation.getBrandId()).getName();
        PmsCategoryBrandRelation categoryBrandRelation=new PmsCategoryBrandRelation();
        categoryBrandRelation.setBrandId(brandRelation.getBrandId());
        categoryBrandRelation.setBrandName(brandName);
        categoryBrandRelation.setCatelogId(brandRelation.getCatelogId());
        categoryBrandRelation.setCatelogName(catelogName);
        int insert = brandRelationService.insert(categoryBrandRelation);
        if(insert>1){
            return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());
        }else{
            return Result.r(BizCodeEnum.ERROR.getCode(),BizCodeEnum.ERROR.getMsg());
        }
    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[] ids){
        List<Long> idList= Arrays.asList(ids);
        int i = brandRelationService.batchDelete(idList);
        if(i>1){
            return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg());
        }else{
            return Result.r(BizCodeEnum.ERROR.getCode(),BizCodeEnum.ERROR.getMsg());
        }
    }


    @RequestMapping("/brands/list")
    public Result ralationBrandList(@RequestParam(value = "catId") String catId){
        List<PmsCategoryBrandRelation> categoryBrandRelations = brandRelationService.getBrandsByCatelogId(Long.valueOf(catId));
        List<RalationBrandVo> ralationBrandVos=new LinkedList<>();
        categoryBrandRelations.stream().map(item->{
            RalationBrandVo ralationBrandVo=new RalationBrandVo();
            ralationBrandVo.setBrandId(item.getBrandId());
            ralationBrandVo.setBrandName(item.getBrandName());
            return ralationBrandVos.add(ralationBrandVo);
        }).collect(Collectors.toList());

        return Result.r(BizCodeEnum.OK.getCode(),BizCodeEnum.OK.getMsg()).put("data",ralationBrandVos);
    }

}

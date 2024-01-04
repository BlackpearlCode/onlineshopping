package com.onlineshopping.product.controller;

import com.onlineshopping.common.constant.ProductConstant;
import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsAttr;
import com.onlineshopping.product.entity.PmsAttrAttrgroupRelation;
import com.onlineshopping.product.entity.PmsProductAttrValue;
import com.onlineshopping.product.service.impl.PmsAttrAttrgroupRelationServiceImpl;
import com.onlineshopping.product.service.impl.PmsAttrServiceImpl;
import com.onlineshopping.product.service.impl.PmsProductAttrValueServiceImpl;
import com.onlineshopping.product.vo.AttrInfoVo;
import com.onlineshopping.product.vo.AttrVo;
import com.onlineshopping.product.vo.ListForspuIdVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("product/attr")
public class AttrController {

    @Autowired
    private PmsAttrServiceImpl attrService;

    @Autowired
    private PmsAttrAttrgroupRelationServiceImpl attrgroupRelationService;

    @Autowired
    private PmsProductAttrValueServiceImpl productAttrValueService;






    @RequestMapping("/save")
    public Result save(@RequestBody AttrVo attrVo){
        PmsAttr pmsAttr=new PmsAttr();
        //属性赋值
        BeanUtils.copyProperties(attrVo,pmsAttr);
        attrService.insert(pmsAttr);
        //判断是添加销售属性还是基本属性；如果是添加销售属性，不添加分组关联
        if(attrVo.getAttrType()== ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode()){
            return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
        }
        //关联表添加数据
        PmsAttrAttrgroupRelation attrgroupRelation=new PmsAttrAttrgroupRelation();
        attrgroupRelation.setAttrId(pmsAttr.getAttrId());
        attrgroupRelation.setAttrGroupId(attrVo.getAttrGroupId());
        attrgroupRelationService.insert(attrgroupRelation);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    @RequestMapping("/{type}/list/{catelogId}")
    public Result baseAttrList( @RequestParam int page, @RequestParam int limit,@RequestParam String key,@PathVariable("catelogId") Long catelogId,@PathVariable("type")String type){
        PageEntity pageEntity = attrService.selectAll(page, limit,key,catelogId, type);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    @RequestMapping("/attrSale/list/{catelogId}")
    public Result baseAttrList( @RequestParam int page, @RequestParam int limit,@PathVariable("catelogId") Long catelogId){
        String key="";
        String attrType="0";
        PageEntity pageEntity = attrService.selectAll(page,limit,key,catelogId, attrType);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    /**
     * 根据attrId查询规格参数详情
     * @param attrId
     * @return
     */
    @RequestMapping("/info/{attrId}")
    public Result info(@PathVariable("attrId") Long attrId){
        AttrInfoVo attrInfoVo = attrService.selectAttrInfo(attrId);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("attr",attrInfoVo);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody AttrInfoVo attrInfoVo){
        PmsAttr attr=new PmsAttr();
        BeanUtils.copyProperties(attrInfoVo,attr);
        attrService.updateByPrimaryKeySelective(attr);
        //判断是修改销售属性还是基本属性；如果是修改销售属性，不修改分组数据
        if(attrInfoVo.getAttrType()== ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode()){
            return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
        }
        Long attrGroupId = attrInfoVo.getAttrGroupId();
        Long attrId = attrInfoVo.getAttrId();
        attrgroupRelationService.updateByAttrId(attrGroupId,attrId);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     *根据attrId批量删除
     */
    @RequestMapping("/delete")
    public Result batchDeleteByAttrId(@RequestBody List<Long> attrIds){
        attrService.batchDeleteByAttrId(attrIds);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 根据spuid查询规格参数信息
     * @param spuId
     * @return
     */
    @RequestMapping("/base/listforspu/{id}")
    public Result findAttrValue(@PathVariable("id") Long spuId){
        List<PmsProductAttrValue> list=productAttrValueService.selectBySpuId(spuId);
        List<ListForspuIdVo> voList = list.stream().map(item -> {
            ListForspuIdVo vo = new ListForspuIdVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("data",voList);
    }

    @RequestMapping("/update/{id}")
    public Result updateAttrValue(@RequestBody List<ListForspuIdVo> vo){
        productAttrValueService.updateBySpuIds(vo);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }
}

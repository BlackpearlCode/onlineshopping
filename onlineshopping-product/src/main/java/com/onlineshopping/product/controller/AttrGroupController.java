package com.onlineshopping.product.controller;


import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsAttrAttrgroupRelation;
import com.onlineshopping.product.entity.PmsAttrGroup;
import com.onlineshopping.product.service.impl.PmsAttrAttrgroupRelationServiceImpl;
import com.onlineshopping.product.service.impl.PmsAttrGroupServiceImpl;
import com.onlineshopping.product.service.impl.PmsAttrServiceImpl;
import com.onlineshopping.product.vo.AttrGroupRelationVo;
import com.onlineshopping.product.vo.AttrGroupWithAttrsVo;
import com.onlineshopping.product.vo.AttrInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {

    @Autowired
    private PmsAttrGroupServiceImpl attrGroupService;

    @Autowired
    private PmsAttrAttrgroupRelationServiceImpl attrgroupRelationService;

    @Autowired
    private PmsAttrServiceImpl attrService;

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public Result list( @PathVariable long catelogId, int page, int limit, String key){
        PageEntity pageEntity = attrGroupService.selectAll(catelogId,page, limit, key);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    /**
     * 新增
     */
    @RequestMapping("/save")
    public Result save(@RequestBody PmsAttrGroup attrGroup){
        attrGroupService.insert(attrGroup);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[] attrGroupIds){
        int num = attrGroupService.batchDelete(attrGroupIds);
        if(num<1){
            return Result.r(BizCodeEnum.ERROR.getCode(), BizCodeEnum.ERROR.getMsg());
        }else{
            return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
        }

    }
    @RequestMapping("/info/{id}")
    public Result attrGroupInfo(@PathVariable Long id){
        PmsAttrGroup attrGroupInfo = attrGroupService.selectByPrimaryKey(id);
        attrGroupInfo.setCatelogPath(attrGroupService.catelogPath(attrGroupInfo.getCatelogId()));
        if(null!= attrGroupInfo){
            return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("attrGroup",attrGroupInfo);
        }else{
            return Result.r(BizCodeEnum.ERROR.getCode(), BizCodeEnum.ERROR.getMsg());
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody PmsAttrGroup attrGroup){
        PmsAttrGroup attrGroupInfo = attrGroupService.selectByPrimaryKey(attrGroup.getAttrGroupId());
        if(attrGroupInfo.equals(attrGroup)){
            return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
        }else{
            attrGroupService.updateByPrimaryKeySelective(attrGroup);
            return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
        }
    }

    /**
     * 关联列表
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public Result getAttrGroupInfo(@PathVariable("attrgroupId") Long attrGroupId){
        //根据分类组id对应的关联表属性id
        List<Long> attrIds = attrgroupRelationService.selectByGroupId(attrGroupId);
        if(attrIds.size()==0){
            return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
        }
        //根据属性id批量查询对应的关联信息
        List<AttrInfoVo> attrInfoVoList = attrService.selectBatchByAttrIds(attrIds);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("data",attrInfoVoList);
    }


    @RequestMapping("/attr/relation/delete")
    public Result deleteRelation(@RequestBody AttrGroupRelationVo[] relationVo){
        attrgroupRelationService.deleteRelation(relationVo);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    @RequestMapping("/{attrgroupId}/noattr/relation")
    public Result attrNoRelation(@PathVariable("attrgroupId") long attrgroupId, int page, int limit, String key){
        PageEntity pageEntity = attrService.getRelation(attrgroupId, page, limit, key);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    @RequestMapping("attr/relation")
    public Result attrRelationInsert(@RequestBody List<AttrGroupRelationVo> groupRelationVo){
        List<PmsAttrAttrgroupRelation> collect = groupRelationVo.stream().map(item -> {
            PmsAttrAttrgroupRelation attrgroupRelation = new PmsAttrAttrgroupRelation();
            attrgroupRelation.setAttrGroupId(item.getAttrGroupId());
            attrgroupRelation.setAttrId(item.getAttrId());
            return attrgroupRelation;
        }).collect(Collectors.toList());
        attrgroupRelationService.batchInsert(collect);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 通过分类id查询所属分组信息，以及每组分组对应的属性信息
     * @param catelogId
     * @return
     */
    @RequestMapping("/{catelogId}/withattr")
    public Result getAttrGroupWithAttrs(@PathVariable Long catelogId){
       List<AttrGroupWithAttrsVo> attrGroupWithAttrsVos= attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("data",attrGroupWithAttrsVos);
    }



}

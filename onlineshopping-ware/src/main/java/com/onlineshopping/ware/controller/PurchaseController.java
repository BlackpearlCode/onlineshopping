package com.onlineshopping.ware.controller;

import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.ware.entity.Purchase;
import com.onlineshopping.ware.service.serviceImpl.PurchaseServiceImpl;
import com.onlineshopping.ware.vo.MergeVo;
import com.onlineshopping.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("ware/purchase")
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseServiceImpl purchaseService;

    /**
     * 查询列表
     */
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String,Object> params){
        PageEntity pageEntity=purchaseService.getAll(params);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    /**
     * 新增
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Purchase purchase){
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
        purchaseService.insertSelective(purchase);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     *查询未领取和新健状态的采购单
     */
    @RequestMapping("/unreceive/list")
    public Result unreceivelist(){
        List<Purchase> purchaseList= purchaseService.getUnreceiveList();
        Map<String,Object> map=new HashMap<>();
        map.put("pageList",purchaseList);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",map);
    }

    /**
     * 修改采购单
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Purchase purchase){
        purchaseService.updateByPrimaryKeySelective(purchase);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 批量合并采购单
     */
    @RequestMapping("/merge")
    public Result merge(@RequestBody MergeVo mergeVo){
        purchaseService.mergePurchase(mergeVo);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 领取采购单
     */
    @RequestMapping("/received")
    public Result received(@RequestBody List<Long> ids){
        purchaseService.received(ids);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 完成采购单
     * @param doneVos
     * @return
     */
    @RequestMapping("/done")
    public Result finish(@RequestBody PurchaseDoneVo doneVos){
        purchaseService.done(doneVos);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 批量删除采购信息
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody List<Long> ids){
        purchaseService.batchDel(ids);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }
}

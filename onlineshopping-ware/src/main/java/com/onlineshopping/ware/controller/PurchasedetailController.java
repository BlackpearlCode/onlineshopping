package com.onlineshopping.ware.controller;

import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.ware.entity.PurchaseDetail;
import com.onlineshopping.ware.service.serviceImpl.PurchaseDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ware/purchasedetail")
public class PurchasedetailController {

    @Autowired
    private PurchaseDetailServiceImpl purchaseDetailService;

    /**
     * 新增
     */
    @RequestMapping("/save")
    public Result save(@RequestBody PurchaseDetail purchaseDetail){
        purchaseDetailService.insertSelective(purchaseDetail);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 查询所有
     */
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String,Object> params){
        PageEntity pageEntity=purchaseDetailService.getAll(params);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    /**
     * 批量删除采购单
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody List<Long> ids){
        purchaseDetailService.batchDelete(ids);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }
}

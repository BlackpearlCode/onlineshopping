package com.onlineshopping.ware.controller;

import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.ware.exception.NoStockException;
import com.onlineshopping.ware.service.serviceImpl.WareSkuServiceImpl;
import com.onlineshopping.ware.vo.SkusHasStockVo;
import com.onlineshopping.ware.vo.WareSkuLockVo;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.onlineshopping.common.utils.Result.r;

@RequestMapping("ware/waresku")
@RestController
@Slf4j
public class WareSkuController {

    @Autowired
    private WareSkuServiceImpl wareSkuService;

    @RequestMapping("/list")
    public Result<List<SkusHasStockVo>> list(@RequestParam Map<String,Object> params){
        PageEntity pageEntity=wareSkuService.getAll(params);
        return r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    //查询sku是否有库存
    @RequestMapping("/hasStock")
    public Result getSkusHasStock(@RequestBody List<Long> skuIds){

        List<SkusHasStockVo> hasStockVos=wareSkuService.getSkusHasStock(skuIds);
        log.info("result:{}",hasStockVos.toString());
        return Result.ok().setData(hasStockVos);
    }

    /**
     * 锁定库存
     * @param vo
     * @return
     */
    @PostMapping("/lock/order")
    public Result orderLockStock(@RequestBody WareSkuLockVo vo){
        try {
            log.info("globalTransactional begin, Xid:{}", RootContext.getXID());
            Boolean stock=wareSkuService.orderLockStock(vo);
            return Result.ok();
        }catch (NoStockException e){
            return Result.error(BizCodeEnum.NO_STOCK_EXCEPTION.getCode(),BizCodeEnum.NO_STOCK_EXCEPTION.getMsg());
        }

    }
}

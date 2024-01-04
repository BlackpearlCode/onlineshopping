package com.onlineshopping.ware.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.common.constant.WarecConstant;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.ware.entity.Purchase;
import com.onlineshopping.ware.entity.PurchaseDetail;
import com.onlineshopping.ware.entity.WareSku;
import com.onlineshopping.ware.feign.ProductFeignService;
import com.onlineshopping.ware.mapper.PurchaseDetailMapper;
import com.onlineshopping.ware.mapper.PurchaseMapper;
import com.onlineshopping.ware.mapper.WareSkuMapper;
import com.onlineshopping.ware.service.PurchaseService;
import com.onlineshopping.ware.vo.MergeVo;
import com.onlineshopping.ware.vo.PurchaseDoneVo;
import com.onlineshopping.ware.vo.PurchaseItemDoneVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Resource
    private PurchaseMapper purchaseMapper;
    @Resource
    private PurchaseDetailMapper purchaseDetailMapper;

    @Resource
    private WareSkuMapper wareSkuMapper;

    @Resource
    private ProductFeignService productFeignService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return purchaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Purchase record) {
        return purchaseMapper.insert(record);
    }

    @Override
    public int insertSelective(Purchase record) {
        return purchaseMapper.insertSelective(record);
    }

    @Override
    public Purchase selectByPrimaryKey(Long id) {
        return purchaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Purchase record) {
        return purchaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Purchase record) {
        return purchaseMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity getAll(Map<String, Object> params) {

        List<Purchase> purchaseList=purchaseMapper.getAll(params);
        PageHelper.startPage(Integer.parseInt((String) params.get("page")),Integer.parseInt((String) params.get("limit")));
        PageInfo<Purchase> pageInfo=new PageInfo<>(purchaseList);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
        return pageEntity;
    }

    @Override
    public List<Purchase> getUnreceiveList() {

        return purchaseMapper.getUnreceiveList();
    }
    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        //创建新订单
        if(null==purchaseId){
            Purchase purchase=new Purchase();
            purchase.setUpdateTime(new Date());
            purchase.setCreateTime(new Date());
            purchase.setStatus(WarecConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseMapper.insertSelective(purchase);
            purchaseId=purchase.getId();
        }
        //如果采购单状态为1或2，则无法合并采购单

        //获取采购单集合
        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetail> purchaseDetailList=items.stream().map(item->{
            PurchaseDetail purchaseDetail = purchaseDetailMapper.selectByPrimaryKey(item);
            purchaseDetail.setPurchaseId(finalPurchaseId);
            purchaseDetail.setStatus(WarecConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetail;
        }).collect(Collectors.toList());
        purchaseDetailMapper.batchUpdate(purchaseDetailList);
        Purchase purchase=new Purchase();
        purchase.setId(purchaseId);
        purchase.setUpdateTime(new Date());
        purchaseMapper.updateByPrimaryKeySelective(purchase);
    }

    /**
     *
     * @param ids：采购单id
     */
    @Transactional
    @Override
    public void received(List<Long> ids) {
        //1.确认当前采购单是新建或已分配状态
        List<Purchase> purchases = ids.stream().map(id -> {
            Purchase purchase = purchaseMapper.selectByPrimaryKey(id);
            return purchase;
        }).filter(item->{
         if(item.getStatus()==WarecConstant.PurchaseStatusEnum.CREATED.getCode() ||
                 item.getStatus()==WarecConstant.PurchaseStatusEnum.ASSIGNED.getCode()){
             return true;
         }
         return false;
        }).map(item->{
            item.setUpdateTime(new Date());
            item.setStatus(WarecConstant.PurchaseStatusEnum.RECEIVE.getCode());
            return item;
        }).collect(Collectors.toList());
        if(!purchases.isEmpty()){
            //2.改变采购单的状态
            purchaseMapper.batchUpdate(purchases);
            //3.改变采购项的状态

            List<PurchaseDetail> purchaseDetailList = purchaseDetailMapper.listDetailById(ids);
            purchaseDetailList.forEach(purchaseDetail -> {
                purchaseDetail.setStatus(WarecConstant.PurchaseDetailStatusEnum.RECEIVE.getCode());
                purchaseDetailMapper.updateByPrimaryKeySelective(purchaseDetail);
            });
        }

    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo doneVo) {

        //2.改变采购单中的采购项状态
        AtomicReference<Boolean> flag= new AtomicReference<>(true);
        List<PurchaseItemDoneVo> items = doneVo.getItems();
        List<PurchaseDetail> purchaseDetailList=items.stream().map(item->{
            PurchaseDetail purchaseDetail=new PurchaseDetail();
            //判断是否采购失败；true:采购失败；false:采购成功
            if(item.getStatus()==WarecConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
                flag.set(false);
                purchaseDetail.setStatus(WarecConstant.PurchaseDetailStatusEnum.HASERROR.getCode());
            }else{
                purchaseDetail.setStatus(WarecConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                //3.将成功采购的进行入库
                //根据id查询采购项信息
                List<PurchaseDetail> purchaseDetails = purchaseDetailMapper.listDetailById(Collections.singletonList(item.getItemId()));
                purchaseDetails.forEach(detail->{
                    WareSku wareSku = new WareSku();
                    wareSku.setWareId(detail.getWareId());
                    wareSku.setSkuId(detail.getSkuId());
                    wareSku.setStock(detail.getSkuNum());
                    //TODO 远程查询sku名称
                    try{
                        Result info = productFeignService.info(detail.getSkuId());
                        Map<String,Object> sku = (Map<String, Object>) info.get("sku");
                        wareSku.setSkuName((String) sku.get("skuName"));
                    }catch (Exception e){

                    }

                    wareSku.setStockLocked(0);
                    //判断是否存在库存记录
                    WareSku wareSkuInfo=wareSkuMapper.selectBySkuIdAndWareId(detail.getSkuId(),detail.getWareId());
                    if(null==wareSkuInfo){
                        wareSkuMapper.insert(wareSku);
                    }else{
                        wareSkuMapper.updateByPrimaryKeySelective(wareSku);
                    }
                });
            }
            purchaseDetail.setId(item.getItemId());
            return purchaseDetail;
        }).collect(Collectors.toList());
        purchaseDetailMapper.batchUpdate(purchaseDetailList);

        //1.改变采购单状态
        Long purchaseId = doneVo.getId();
        Purchase purchase=new Purchase();
        purchase.setId(purchaseId);
        if(flag.get()){
            purchase.setStatus(WarecConstant.PurchaseStatusEnum.FINISH.getCode());
        }else{
            purchase.setStatus(WarecConstant.PurchaseStatusEnum.HASERROR.getCode());
        }
        purchase.setUpdateTime(new Date());
        purchaseMapper.updateByPrimaryKeySelective(purchase);


    }

    @Override
    public void batchDel(List<Long> ids) {
        purchaseMapper.batchDel(ids);
    }

}

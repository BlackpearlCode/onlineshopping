package com.onlineshopping.ware.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.ware.entity.WareSku;
import com.onlineshopping.ware.exception.NoStockException;
import com.onlineshopping.ware.mapper.WareSkuMapper;
import com.onlineshopping.ware.service.WareSkuService;
import com.onlineshopping.ware.vo.OrderItemVo;
import com.onlineshopping.ware.vo.SkuWareHasStock;
import com.onlineshopping.ware.vo.SkusHasStockVo;
import com.onlineshopping.ware.vo.WareSkuLockVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WareSkuServiceImpl implements WareSkuService{

    @Resource
    private WareSkuMapper wareSkuMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return wareSkuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WareSku record) {
        return wareSkuMapper.insert(record);
    }

    @Override
    public int insertSelective(WareSku record) {
        return wareSkuMapper.insertSelective(record);
    }

    @Override
    public WareSku selectByPrimaryKey(Long id) {
        return wareSkuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WareSku record) {
        return wareSkuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WareSku record) {
        return wareSkuMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity getAll(Map<String, Object> params) {

        List<WareSku> wareSkuList=wareSkuMapper.getAll(params);
        PageHelper.startPage(Integer.parseInt((String) params.get("page")),Integer.parseInt((String) params.get("limit")));
        PageInfo<WareSku> pageInfo=new PageInfo<>(wareSkuList);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
        return pageEntity;
    }

    @Override
    public List<SkusHasStockVo> getSkusHasStock(List<Long> skuIds) {
        List<SkusHasStockVo> stockVos = skuIds.stream().map(skuId -> {
            SkusHasStockVo vo = new SkusHasStockVo();
            int count;
            //判断该商品是否有库存记录，如果没有库存记录设置count为0
            if(null==wareSkuMapper.selectInfoBySkuId(skuId)){
                count=0;
            }else{
                count=wareSkuMapper.selectBySkuId(skuId);
            }
            Boolean bool=count >0 ?true:false;
            vo.setSkuId(skuId);
            vo.setHasStock(bool);
            return vo;
        }).collect(Collectors.toList());
        return stockVos;
    }

    /**
     * 为某个订单锁定库存
     * (rollbackFor =NoStockException.class )
     * 默认只要运行时异常都会回滚
     * @param vo
     * @return
     */
    @Transactional(rollbackFor =NoStockException.class )
    @Override
    public Boolean orderLockStock(WareSkuLockVo vo) {
        //1. 按照下单的收货地址，找到一个就近仓库，锁定库存
        //找到每个商品在那个仓库都有库存
        List<OrderItemVo> locks = vo.getLocks();
        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            Long skuId = item.getSkuId();
            SkuWareHasStock stock = new SkuWareHasStock();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());
            //查询这个商品在哪里有库存
            List<Long> wareIds=wareSkuMapper.listWareIdHasStock(skuId);
            stock.setWareId(wareIds);
            return stock;
        }).collect(Collectors.toList());
        //2. 锁定库存
        for (SkuWareHasStock hasStock : collect) {

            Boolean skuStocked=false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();
            if(CollectionUtils.isEmpty(wareIds)){
                //没有任何仓库有这个商品的库存
                throw new NoStockException(skuId);
            }
            for (Long wareId:wareIds){
                //成功返回1，失败返回0
                Long count=wareSkuMapper.lockStock(skuId,wareId,hasStock.getNum());
                if(count==1){
                    skuStocked=true;
                    break;
                }else{
                    //当前仓库锁失败，尝试下一个仓库
                }
            }
            if(skuStocked==false){
                //当前商品所有仓库都没锁住
                throw new NoStockException(skuId);
            }
        }
        //肯定全部商品锁定成功
        return true;
    }

}

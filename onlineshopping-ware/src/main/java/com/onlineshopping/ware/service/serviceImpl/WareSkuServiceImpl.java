package com.onlineshopping.ware.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.onlineshopping.common.to.mq.OrderTo;
import com.onlineshopping.common.to.mq.StockDetailTo;
import com.onlineshopping.common.to.mq.StockLockedTo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.ware.entity.WareOrderTask;
import com.onlineshopping.ware.entity.WareOrderTaskDetail;
import com.onlineshopping.ware.entity.WareSku;
import com.onlineshopping.ware.exception.NoStockException;
import com.onlineshopping.ware.feign.OrderFeignService;
import com.onlineshopping.ware.mapper.WareSkuMapper;
import com.onlineshopping.ware.service.WareOrderTaskDetailService;
import com.onlineshopping.ware.service.WareOrderTaskService;
import com.onlineshopping.ware.service.WareSkuService;
import com.onlineshopping.ware.vo.*;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j

@Service
public class WareSkuServiceImpl implements WareSkuService{

    @Resource
    private WareSkuMapper wareSkuMapper;

    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderFeignService orderFeignService;



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
        /**
         * 报存库存工作单
         */
        WareOrderTask wareOrderTask = new WareOrderTask();
        wareOrderTask.setOrderSn(vo.getOrderSn());
        wareOrderTaskService.save(wareOrderTask);
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
            //1.如果每一个商品都锁定成功，将当前商品锁定了几件的工作单的记录发送给MQ
            //2.锁定失败，前面保存的工作单消息就回滚了。发送出去的消息，即使要解锁记录，由于去数据库查不到id,所以就不用解锁
            for (Long wareId:wareIds){
                //成功返回1，失败返回0
                Long count=wareSkuMapper.lockStock(skuId,wareId,hasStock.getNum());
                if(count==1){
                    skuStocked=true;
                    WareOrderTaskDetail wareOrderTaskDetail = new WareOrderTaskDetail();
                    wareOrderTaskDetail.setSkuId(skuId);
                    wareOrderTaskDetail.setSkuNum(hasStock.getNum());
                    wareOrderTaskDetail.setTaskId(wareOrderTask.getId());
                    wareOrderTaskDetail.setWareId(wareId);
                    wareOrderTaskDetail.setLockStatus(1);
                    wareOrderTaskDetailService.save(wareOrderTaskDetail);
                    StockLockedTo stockLockedTo = new StockLockedTo();
                    stockLockedTo.setId(wareOrderTask.getId());
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(wareOrderTaskDetail,stockDetailTo);
                    stockLockedTo.setDetailTo(stockDetailTo);
                    rabbitTemplate.convertAndSend("stock-event-exchange","stock.locked",stockLockedTo);
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

    @Override
    public void unlockStock(StockLockedTo stockLockedTo) {
        Long id = stockLockedTo.getId();
        StockDetailTo detail = stockLockedTo.getDetailTo();
        Long detailId = detail.getId();
        /**
         * 解锁：查询数据库关于这个订单的锁定库存消息
         *      有：证明库存锁定成功
         *          解锁：订单情况
         *              1.没有这个订单，必须解锁
         *              2.有这个订单，不是解锁库存
         *                  订单状态：已取消：解锁库存
         *                          没取消：不能解锁库存
         *      没有：库存锁定失败，库存回滚，这种情况下无需解锁
         */
        WareOrderTaskDetail wareOrderTaskDetail = wareOrderTaskDetailService.selectByPrimaryKey(detailId);
        if(wareOrderTaskDetail!=null){
            //解锁
            Long stockId = stockLockedTo.getId();
            WareOrderTask wareOrderTask = wareOrderTaskService.selectByPrimaryKey(stockId);
            String orderSn = wareOrderTask.getOrderSn();
            Result result=orderFeignService.getOrderStatus(orderSn);
            if(result.getCode()==0){
                //订单数据返回成功
                Gson gson=new Gson();
                String json = gson.toJson(result.get("data"));
                OrderVo orderVo = gson.fromJson(json, OrderVo.class);
                if(orderVo==null || orderVo.getStatus()==4){
                    //订单不存在或订单已经被取消，解锁库存
                    if(wareOrderTaskDetail.getLockStatus()==1){
                        //当前库存工作清单状态为1，已锁定但是未解锁才能解锁
                        unLockStock(detail.getSkuId(),detail.getWareId(),detail.getSkuNum(),detailId);
                    }
                }
            }else{
                throw new RuntimeException("远程服务调用失败");
            }
        }else{
            //无需解锁

        }
    }

    //防止订单服务卡顿，导致订单状态一直改不了，库存消息优先到期。查询订单状态为新键状态，什么都不做都走了
    //导致卡顿的订单，永远也不能解锁库存
    @Override
    @Transactional
    public void unlockStock(OrderTo order) {
        String orderSn = order.getOrderSn();
        //查询最新的库存的状态，防止重复解锁库存
        WareOrderTask orderTask=wareOrderTaskService.getOrderTaskByOrderSn(orderSn);
        Long id = orderTask.getId();
        //按照工作单找到所有没有解锁的库存，进行解锁
        List<WareOrderTaskDetail> detailList=wareOrderTaskDetailService.getOrderTaskDetailByTaskId(id);
        if(!CollectionUtils.isEmpty(detailList)){
            detailList.stream().forEach(item->{
                unLockStock(item.getSkuId(),item.getWareId(),item.getSkuNum(),item.getId());
            });
        }

    }


    /**
     * 解锁库存
     * @param skuId :商品id
     * @param wareId：仓库id
     * @param num：解锁数量
     * @param taskDetailId
     */
    @Transactional
    public void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId){
        //库存解锁
        wareSkuMapper.unlockStock(skuId,wareId,num);
        //更新库存工作单的状态
        WareOrderTaskDetail wareOrderTaskDetail = new WareOrderTaskDetail();
        wareOrderTaskDetail.setId(taskDetailId);
        wareOrderTaskDetail.setLockStatus(2);
        wareOrderTaskDetailService.updateByPrimaryKeySelective(wareOrderTaskDetail);

    }

}

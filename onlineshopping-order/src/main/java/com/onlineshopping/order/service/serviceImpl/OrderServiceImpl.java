package com.onlineshopping.order.service.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onlineshopping.common.to.mq.OrderTo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.common.vo.TokenInfo;
import com.onlineshopping.order.constant.OrderConstant;
import com.onlineshopping.order.entity.Order;
import com.onlineshopping.order.entity.OrderItem;
import com.onlineshopping.order.entity.PaymentInfo;
import com.onlineshopping.order.enume.OrderStatusEnum;
import com.onlineshopping.order.exception.NoStockException;
import com.onlineshopping.order.feign.*;
import com.onlineshopping.order.interceptor.LoginUserInterceptor;
import com.onlineshopping.order.mapper.OrderMapper;
import com.onlineshopping.order.service.OrderItemService;
import com.onlineshopping.order.service.OrderService;
import com.onlineshopping.order.service.PaymentInfoService;
import com.onlineshopping.order.to.OrderCreateTo;
import com.onlineshopping.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private CartFeignService cartFeignService;

    @Autowired
    private WmsFeignService wmsFeignService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private RedisFeignService redisFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentInfoService paymentInfoService;

    private ThreadLocal<OrderSubmitVo> orderSubmitVoThreadLocal=new ThreadLocal<>();

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Order record) {
        return orderMapper.insert(record);
    }

    @Override
    public int insertSelective(Order record) {
        return orderMapper.insertSelective(record);
    }

    @Override
    public Order selectByPrimaryKey(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        return orderMapper.updateByPrimaryKey(record);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {

        OrderConfirmVo confirmVo=new OrderConfirmVo();
        //获取用户信息
        TokenInfo tokenInfo = LoginUserInterceptor.loginUser.get();
        if(tokenInfo==null){
            return null;
        }

        String memberId="";
        if(!StringUtils.isEmpty(tokenInfo.getUserId())){
            //如果用户是账号登录
            memberId=tokenInfo.getUserId();
        }else{
            //如果用户是第三方登录，
            memberId=tokenInfo.getSocialUid();
        }
        //查询用户信息
        //获取之前的请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String finalMemberId = memberId;
        CompletableFuture<MemberVo> memberFuture = CompletableFuture.supplyAsync(() -> {
            //每一个线程都共享之前的数据
            RequestContextHolder.setRequestAttributes(requestAttributes);

            MemberVo memberInfo = memberFeignService.findMemberId(finalMemberId);
            //查询用户积分
            int integration = memberInfo.getIntegration() == null ? 0 : memberInfo.getIntegration();
            confirmVo.setIntegration(integration);
            //设置商品总数
            confirmVo.setCount(confirmVo.getCount());
            //设置总价
            confirmVo.setTotal(confirmVo.getTotal());
            //设置应付总价
            confirmVo.setPayPrice(confirmVo.getTotal());
           return memberInfo;
        },executor);
        System.out.println("memberFuture---->"+ memberFuture.get());
        MemberVo memberVo = memberFuture.get();
        CompletableFuture<Void> addressFuture = memberFuture.thenAcceptAsync((res) -> {
            //每一个线程都共享之前的数据
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddressVo> address = memberFeignService.getAddress(res.getId());
            confirmVo.setAddress(address);

        }, executor);

        CompletableFuture<Void> tokenFuture = memberFuture.thenAcceptAsync((res) -> {
            //每一个线程都共享之前的数据
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //TODO 防重令牌
            String token = UUID.randomUUID().toString().replace("_", "");
            redisFeignService.saveToken(OrderConstant.USER_ORDER_TOKEN_PREFIX + res.getId(), token, 30 * 60);
            confirmVo.setOrderToken(token);

        }, executor);


        CompletableFuture<Void> cartuture = CompletableFuture.runAsync(() -> {
            //每一个线程都共享之前的数据
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //2.远程查询购物车所有选中的购物项
            List<OrderItemVo> currentUserCartItems = cartFeignService.getCurrentUserCartItems();
            confirmVo.setItems(currentUserCartItems);

        }, executor).thenRunAsync(()->{
            //获取所有购物项
            List<OrderItemVo> items = confirmVo.getItems();
            if(!CollectionUtils.isEmpty(items)){
                //获取所有商品id
                List<Long> skuIds = items.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
                Result skusHasStock = wmsFeignService.getSkusHasStock(skuIds);
                Object data = skusHasStock.get("data");
                Gson gson=new Gson();
                List<SkuStockVo> skuStockVos =gson.fromJson(gson.toJson(data),new TypeToken<List<SkuStockVo>>(){}.getType());
                if(!CollectionUtils.isEmpty(skuStockVos)){
                    Map<Long, Boolean> map = skuStockVos.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                    confirmVo.setStocks(map);
                }
            }
        },executor);

        CompletableFuture.allOf(memberFuture,addressFuture,tokenFuture,cartuture).get();


        return confirmVo;
    }


    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo) {
       // log.info("globalTransactional begin, Xid:{}", RootContext.getXID());
        orderSubmitVoThreadLocal.set(submitVo);
        SubmitOrderResponseVo responseVo = new SubmitOrderResponseVo();
        //获取用户登录信息
        TokenInfo tokenInfo = LoginUserInterceptor.loginUser.get();
        if(tokenInfo==null){
            return null;
        }

        String memberId="";
        if(!StringUtils.isEmpty(tokenInfo.getUserId())){
            //如果用户是账号登录
            memberId=tokenInfo.getUserId();
        }else{
            //如果用户是第三方登录，
            memberId=tokenInfo.getSocialUid();
        }
        //查询用户信息
        MemberVo memberInfo = memberFeignService.findMemberId(memberId);
        responseVo.setCode(0);
        if (memberInfo == null) {
            return null;
        }
        //1.验证令牌【令牌的对比和删除必须保证原子性】
        //0:删除令牌失败；1：删除令牌成功
        String script="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //原子验证令牌和删除令牌
        Long result = redisFeignService.executeLua(script, Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberInfo.getId()), submitVo.getOrderToken());
        if(result==0L){
            //令牌验证失败
            return responseVo;
        }
        //令牌验证成功
        //1.创建订单项信息
        OrderCreateTo order = createOrder();
        //2.验价
        BigDecimal payAmount = order.getOrder().getPayAmount();
        BigDecimal payPrice = submitVo.getPayPrice();
        if(Math.abs(payAmount.subtract(payPrice).doubleValue())<0.01){
            //金额对比成功
            //3.保存订单
            saveOrder(order);
            //4.库存锁定。只要有异常回滚订单数据
            //订单号，所有订单项(skuId,skuName,num)
            WareSkuLockVo wareSkuLockVo = new WareSkuLockVo();
            wareSkuLockVo.setOrderSn(order.getOrder().getOrderSn());
            List<OrderItemVo> locks = order.getItems().stream().map(item -> {
                OrderItemVo orderItemVo = new OrderItemVo();
                orderItemVo.setSkuId(item.getSkuId());
                orderItemVo.setCount(item.getSkuQuantity());
                orderItemVo.setTitle(item.getSkuName());
                return orderItemVo;
            }).collect(Collectors.toList());
            wareSkuLockVo.setLocks(locks);
            //TODO 远程锁库存
            Result r = wmsFeignService.orderLockStock(wareSkuLockVo);
            if(r.getCode()==0){
                //库存锁定成功
                responseVo.setOrder(order.getOrder());
                //TODO 订单创建成功，发送消息给rabbitmq
                rabbitTemplate.convertAndSend("order-event-exchange","order.create.order",order.getOrder());
                return responseVo;
            }else{
                //库存锁定失败
                responseVo.setCode(3);
                throw new NoStockException();
            }

        }else{
            //金额对比失败
            responseVo.setCode(2);
            return responseVo;

        }
    }

    @Override
    public Order getOrderByOrderSn(String orderSn) {
        Order order=orderMapper.selectByOrderSn(orderSn);
        return order;
    }

    @Override
    public void closeOrder(Order order) {
        //查询当前订单的最新状态
        Order orderEntity = orderMapper.selectByPrimaryKey(order.getId());

        if(orderEntity.getStatus()==OrderStatusEnum.CREATE_NEW.getCode().intValue()){
            //关单
            orderEntity.setStatus((byte) OrderStatusEnum.CANCLED.getCode().intValue());
            orderMapper.updateByPrimaryKeySelective(orderEntity);
            //发送消息
            try{
                //TODO 保证消息一定会发送出去，每一个消息做一个日志记录保存到数据库
                OrderTo orderTo = new OrderTo();
                BeanUtils.copyProperties(orderEntity,orderTo);
                rabbitTemplate.convertAndSend("order-event-exchange","order.release.other",orderTo);
            }catch (Exception e){
                //TODO 定期扫描数据库将没有发送成功的消息再次发送一遍
            }

        }
    }

    @Override
    public PayVo getOrderPay(String orderSn) {
        Order order = orderMapper.selectByOrderSn(orderSn);
        PayVo payVo = new PayVo();
        //设置总额
        payVo.setTotal_amount(order.getPayAmount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        //设置订单号
        payVo.setOut_trade_no(order.getOrderSn());
        List<OrderItem> orderItems=orderItemService.selectByOrderSn(orderSn);
        //设置订单主题
        payVo.setSubject(orderItems.get(0).getSkuName());
        //设置备注
        payVo.setBody(orderItems.get(0).getSkuAttrsVals());
        return payVo;
    }

    @Override
    public List<Order> selectByMemberId(Long id) {
        return orderMapper.selectByMemberId(id);
    }

    @Override
    public PageEntity queryPageWithItem(Map<String, Object> params) {
        TokenInfo tokenInfo = LoginUserInterceptor.loginUser.get();
        if(tokenInfo==null){
            return null;
        }

        String memberId="";
        if(!StringUtils.isEmpty(tokenInfo.getUserId())){
            //如果用户是账号登录
            memberId=tokenInfo.getUserId();
        }else{
            //如果用户是第三方登录，
            memberId=tokenInfo.getSocialUid();
        }
        //查询用户信息
        MemberVo memberInfo = memberFeignService.findMemberId(memberId);

        List<Order> orderList=orderService.selectByMemberId(memberInfo.getId());

        List<Order> list =new LinkedList<>();
        for(Order order: orderList){
            String orderSn = order.getOrderSn();
            List<OrderItem> orderItems = orderItemService.selectByOrderSn(orderSn);
            order.setItems(orderItems);
            list.add(order);
        }
        PageHelper.startPage((Integer) params.get("page"),10);
        PageInfo<Order> pageInfo=new PageInfo<>(orderList);
        pageInfo.setList(list);

        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
        return pageEntity;
    }

    @Transactional
    @Override
    public String handlePayResult(PayAsyncVo vo) throws ParseException {
        //保存交易流水
        PaymentInfo payment = new PaymentInfo();
        payment.setAlipayTradeNo(vo.getTrade_no());
        payment.setOrderSn(vo.getOut_trade_no());
        payment.setPaymentStatus(vo.getTrade_status());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        payment.setCallbackTime(format.parse(vo.getNotify_time()));
        paymentInfoService.insertSelective(payment);

        //修改订单状态
        if(vo.getTrade_status().equals("TRADE_SUCCESS")||vo.getTrade_status().equals("TREAD_FINISHED")){
            //支付成功状态
            String outTradeNo = vo.getOut_trade_no();
            Order order=new Order();
            order.setOrderSn(outTradeNo);
            order.setStatus(OrderStatusEnum.PAYED.getCode().byteValue());
            orderMapper.updateByOrderSn(outTradeNo,OrderStatusEnum.PAYED.getCode().byteValue());
            return "success";
        }
        return "error";
    }

    /**
     * 保存订单数据
     * @param order
     */
    private void saveOrder(OrderCreateTo order) {
        Order orderEntity = order.getOrder();
        orderEntity.setModifyTime(new Date());
        //添加订单
        orderMapper.insertSelective(orderEntity);
        List<OrderItem> orderItems = order.getItems();
        //批量添加订单项信息
        orderMapper.saveBatch(orderItems);



    }

    private OrderCreateTo createOrder(){
        OrderCreateTo orderCreateTo = new OrderCreateTo();
        //1.生成订单号
        String orderSn = IdWorker.getTimeId();
        //创建订单号
        Order order=buildOrder(orderSn);
        //2.获取所有选中的购物项
        List<OrderItem> orderItems = buildOrderItems(orderSn);
        //计算价格
        computePrice(order,orderItems);
        orderCreateTo.setOrder(order);
        orderCreateTo.setItems(orderItems);
        return orderCreateTo;
    }

    private void computePrice(Order order, List<OrderItem> orderItems) {
        BigDecimal total = new BigDecimal("0.0");
        BigDecimal coupon = new BigDecimal("0.0");
        BigDecimal integration = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");
        BigDecimal gift = new BigDecimal("0.0");
        BigDecimal growth = new BigDecimal("0.0");
        //订单总额：叠加每一个订单项的总额信息
        for (OrderItem orderItem : orderItems) {
            coupon=coupon.add(orderItem.getCouponAmount()==null?new BigDecimal("0.0"):orderItem.getCouponAmount());
            integration=integration.add( orderItem.getIntegrationAmount()==null?new BigDecimal("0.0"):orderItem.getIntegrationAmount());
            promotion=promotion.add(orderItem.getPromotionAmount()==null?new BigDecimal("0.0"):orderItem.getPromotionAmount());
            total=total.add(orderItem.getRealAmount()==null?new BigDecimal("0.0"):orderItem.getRealAmount());
            gift=gift.add(orderItem.getGiftIntegration()==null?new BigDecimal("0.0"): BigDecimal.valueOf(orderItem.getGiftIntegration()));
            growth=growth.add(orderItem.getGiftGrowth()==null?new BigDecimal("0.0"): BigDecimal.valueOf(orderItem.getGiftGrowth()));
        }
        //1.订单价格相关信息
        order.setTotalAmount(total);
        //设置应付总额(应付总额=订单总额+运费)
        order.setPayAmount(total.add(order.getFreightAmount()));
        //设置促销优化金额（促销价、满减、阶梯价）
        order.setPromotionAmount(promotion);
        //设置积分抵扣金额
        order.setIntegrationAmount(integration);
        //设置优惠券抵扣金额
        order.setCouponAmount(coupon);
        //设置订单的相关状态信息
        order.setStatus(OrderStatusEnum.CREATE_NEW.getCode().byteValue());
        //设置积分信息
        order.setIntegration(gift.intValue());
        //设置成长值
        order.setGrowth(growth.intValue());
        //设置删除状态 0:未删除；1：已删除
        order.setDeleteStatus((byte) 0);
    }

    /**
     * 构建订单
     * @param orderSn
     */
    private Order buildOrder(String orderSn) {
        Order order = new Order();
        //创建订单号
        order.setOrderSn(orderSn);
        TokenInfo tokenInfo = LoginUserInterceptor.loginUser.get();
        if(tokenInfo==null){
            return null;
        }

        String memberId="";
        if(!StringUtils.isEmpty(tokenInfo.getUserId())){
            //如果用户是账号登录
            memberId=tokenInfo.getUserId();
        }else{
            //如果用户是第三方登录，
            memberId=tokenInfo.getSocialUid();
        }
        //查询用户信息
        MemberVo memberInfo = memberFeignService.findMemberId(memberId);
        order.setMemberId(memberInfo.getId());
        //获取收获地址信息
        OrderSubmitVo orderSubmitVo = orderSubmitVoThreadLocal.get();
        Result fare = wmsFeignService.fare(orderSubmitVo.getAddrId());
        Object data = fare.get("data");
        Gson gson=new Gson();
        FareVo fareVo = gson.fromJson(data.toString(), FareVo.class);
        //设置运费金额
        order.setFreightAmount(fareVo.getFare());
        //设置收货所在城市
        order.setReceiverCity(fareVo.getAddress().getCity());
        //设置收货详细地址
        order.setReceiverDetailAddress(fareVo.getAddress().getDetailAddress());
        //设置收货人名字
        order.setReceiverName(fareVo.getAddress().getName());
        //设置收货人电话
        order.setReceiverPhone(fareVo.getAddress().getPhone());
        //设置收货人邮编
        order.setReceiverPostCode(fareVo.getAddress().getPostCode());
        //设置收货人所在省份
        order.setReceiverProvince(fareVo.getAddress().getProvince());
        //设置收货人区域
        order.setReceiverRegion(fareVo.getAddress().getRegion());
        return order;
    }

    /**
     * 构建订单项
     * @return
     */
     
    private List<OrderItem> buildOrderItems(String orderSn) {
        //最后确定每个购物项价格

        List<OrderItemVo> currentUserCartItems = cartFeignService.getCurrentUserCartItems();
        if(!CollectionUtils.isEmpty(currentUserCartItems)){
            List<OrderItem> orderItems = currentUserCartItems.stream().map(cartItem -> {

                OrderItem itemEntity = buildOrderItem(cartItem);
                itemEntity.setOrderSn(orderSn);
                return itemEntity;
            }).collect(Collectors.toList());
            return orderItems;
        }
        return null;
    }

    /**
     * 构建某个订单项
     * @param cartItem
     * @return
     */
    private OrderItem buildOrderItem(OrderItemVo cartItem) {
        //1.订单详细：订单号
        OrderItem orderItem = new OrderItem();
        //2.商品的spu信息
        Long skuId = cartItem.getSkuId();
        Result result = productFeignService.getSpuInfoBySkuId(skuId);
        Object data = result.get("data");
        System.out.println("data--->"+data);
        Gson gson=new Gson();
        SpuInfoVo spuInfoVo = gson.fromJson(data.toString(), SpuInfoVo.class);
        orderItem.setSpuId(spuInfoVo.getId());
        orderItem.setSpuBrand(spuInfoVo.getBrandId().toString());
        orderItem.setSpuName(spuInfoVo.getSpuName());
        orderItem.setCategoryId(spuInfoVo.getCatalogId());
        //3.商品的sku信息
        orderItem.setSkuId(cartItem.getSkuId());
        orderItem.setSkuName(cartItem.getTitle());
        orderItem.setSkuPic(cartItem.getImage());
        orderItem.setSkuPrice(cartItem.getPrice());
        String skuAttr = StringUtils.collectionToDelimitedString(cartItem.getSkuAttr(), ";");
        orderItem.setSkuAttrsVals(skuAttr);
        orderItem.setSkuQuantity(cartItem.getCount());

        //4.优惠信息
        //5.积分信息
        orderItem.setGiftGrowth(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());
        orderItem.setGiftIntegration(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());
        //订单项价格信息
        orderItem.setPromotionAmount(new BigDecimal("0.0"));
        orderItem.setCouponAmount(new BigDecimal("0.0"));
        orderItem.setIntegrationAmount(new BigDecimal("0.0"));
        //当前订单项的实际金额=总额-各种优惠
        BigDecimal orign = orderItem.getSkuPrice().multiply(BigDecimal.valueOf(orderItem.getSkuQuantity()));
        BigDecimal subtract = orign.subtract(orderItem.getPromotionAmount())
                .subtract(orderItem.getCouponAmount())
                .subtract(orderItem.getIntegrationAmount());
        orderItem.setRealAmount(subtract);
        return orderItem;
    }

}

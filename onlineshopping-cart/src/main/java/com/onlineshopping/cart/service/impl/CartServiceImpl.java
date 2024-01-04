package com.onlineshopping.cart.service.impl;

import com.google.gson.Gson;
import com.onlineshopping.cart.feign.ProductFeignService;
import com.onlineshopping.cart.feign.RedisFeignService;
import com.onlineshopping.cart.interceptor.CartInterceptor;
import com.onlineshopping.cart.service.CartService;
import com.onlineshopping.cart.vo.Cart;
import com.onlineshopping.cart.vo.CartItem;
import com.onlineshopping.cart.vo.PmsSkuInfo;
import com.onlineshopping.cart.vo.UserInfoTo;
import com.onlineshopping.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private RedisFeignService redisFeignService;

    @Autowired
    private ThreadPoolExecutor executor;

    private final String CART_PREFIX = "onlineshopping:cart:";
    @Override
    public CartItem addCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        String cartKey = getString();
        //查询购物车中是否有该商品，有则数量累加，没有则添加新商品
        String data = redisFeignService.getHash(cartKey, skuId.toString());
        if(!StringUtils.isEmpty(data)){
            //购物车中有该商品，数量累加
            Gson gson=new Gson();
            CartItem cartItem=gson.fromJson(data, CartItem.class);
            cartItem.setCount(cartItem.getCount()+num);
            cartItem.setTotalPrice(cartItem.getTotalPrice());
            redisFeignService.saveHash(cartKey, skuId.toString(),gson.toJson(cartItem));
            return cartItem;
        }

        //添加新商品到购物车
        CartItem cartItem = new CartItem();
        //异步：远程查询sku信息
        Gson gson = new Gson();
        CompletableFuture<Void> getSkuInfoTask = CompletableFuture.runAsync(() -> {
            Result info = productFeignService.info(skuId);

            Object sku = info.get("sku");
            PmsSkuInfo skuInfoVo = gson.fromJson(gson.toJson(sku), PmsSkuInfo.class);

            cartItem.setCheck(true);
            cartItem.setCount(num);
            cartItem.setImage(skuInfoVo.getSkuDefaultImg());
            cartItem.setSkuId(skuId);
            cartItem.setTitle(skuInfoVo.getSkuTitle());
            cartItem.setPrice(skuInfoVo.getPrice());
            cartItem.setTotalPrice(cartItem.getTotalPrice());
        },executor);
        //异步：查询sku的组合信息
        CompletableFuture<Void> getSkuSaleAttrValues = CompletableFuture.runAsync(() -> {
            List<String> skuSaleAtttrValues = productFeignService.getSkuSaleAtttrValues(skuId);
            cartItem.setSkuAttr(skuSaleAtttrValues);
        }, executor);
        CompletableFuture.allOf(getSkuInfoTask,getSkuSaleAttrValues).get();
        redisFeignService.saveHash(cartKey, String.valueOf(skuId),gson.toJson(cartItem));

        return cartItem;
    }

    //获取购物车key
    private String getString() {
        //获取当前用户信息
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        String cartKey="";
        if (userInfoTo.getUserId()!= null) {
            //登录状态下，添加购物车
            cartKey=CART_PREFIX+userInfoTo.getUserId();
        }else {
            //未登录状态下，添加购物车
            cartKey=CART_PREFIX+userInfoTo.getUserKey();
        }
        return cartKey;
    }

    @Override
    public CartItem getCartItem(Long skuId) {
        String cartKey = getString();
        String data = redisFeignService.getHash(cartKey, skuId.toString());
        Gson gson=new Gson();
        CartItem cartItem=gson.fromJson(data, CartItem.class);
        return cartItem;
    }

    @Override
    public Cart getAllCarts() {
        //获取当前用户信息
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();

        //判断用户是否登录
        if(userInfoTo.getUserId()==null){
            //用户未登录，获取临时购物车的内容
            Map<String, String> cartItemMap=redisFeignService.getAllHash(CART_PREFIX+userInfoTo.getUserKey());
            Collection<String> values = cartItemMap.values();
            List<CartItem> cartItems = values.stream().map(i -> {
                Gson gson = new Gson();
                CartItem cartItem = gson.fromJson( i, CartItem.class);
                return cartItem;
            }).collect(Collectors.toList());
            Cart cart = new Cart();
            cart.setItems(cartItems);
            return cart;
        }
        //用户已登录，获取用户购物车的商品
        Map<String, String> cartUserMap=redisFeignService.getAllHash(CART_PREFIX+userInfoTo.getUserId());
        //获取临时购物车的商品
        Map<String, String> carTemporaryMap=redisFeignService.getAllHash(CART_PREFIX+userInfoTo.getUserKey());
        Collection<String> temporaryValues = carTemporaryMap.values();
        //将临时购物车的商品添加到用户购物车中，如果用户购物车存在该商品则修改数量和总价格；如果用户购物车不存在则添加
        for (String temporaryValue : temporaryValues) {
            Gson gson = new Gson();
            CartItem cartItem = gson.fromJson(temporaryValue, CartItem.class);
            if(cartUserMap.containsKey(String.valueOf(cartItem.getSkuId()))){
                //用户购物车中有该商品，数量累加
                CartItem cartItemUser = gson.fromJson(cartUserMap.get(String.valueOf(cartItem.getSkuId())), CartItem.class);
                cartItemUser.setCount(cartItemUser.getCount()+cartItem.getCount());
                cartItem.setTotalPrice(cartItem.getTotalPrice());
            }
            //如果用户购物车不存在该商品，则添加
            cartUserMap.put(String.valueOf(cartItem.getSkuId()),gson.toJson(cartItem));
        }
        for (Map.Entry<String, String> entry : cartUserMap.entrySet()) {
            redisFeignService.saveHash(CART_PREFIX+userInfoTo.getUserId(),entry.getKey(),entry.getValue());
        }
        //购物车合并成功，删除临时购物车
        clearCart(CART_PREFIX+userInfoTo.getUserKey());
        //将合并后的购物车商品列表返回前端
        Collection<String> values = cartUserMap.values();
        List<CartItem> userCarts = values.stream().map(i -> {
            Gson gson = new Gson();
            CartItem cartItem = gson.fromJson(i, CartItem.class);
            return cartItem;
        }).collect(Collectors.toList());
        Cart cart = new Cart();
        cart.setItems(userCarts);
        return cart;
    }

    @Override
    public void clearCart(String cartKey) {
        redisFeignService.delHash(cartKey);
    }

    @Override
    public void checkItem(Long skuId, Integer checked) {
        CartItem cartItem = getCartItem(skuId);
        if(cartItem!=null){
            cartItem.setCheck(checked==1?true:false);
            String cartKey = getString();
            redisFeignService.saveHash(cartKey, String.valueOf(skuId),new Gson().toJson(cartItem));
        }
    }

    @Override
    public void countItem(Long skuId, Integer num) {
        CartItem cartItem = getCartItem(skuId);
        if(cartItem!=null){
            cartItem.setCount(num);
            String cartKey = getString();
            redisFeignService.saveHash(cartKey, String.valueOf(skuId),new Gson().toJson(cartItem));
        }
    }

    @Override
    public void deleteItem(Integer skuId) {
        String cartKey = getString();
        redisFeignService.delHashKey(cartKey,skuId.toString());
    }

    @Override
    public List<CartItem> getCurrentUserCartItems() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if(userInfoTo.getUserId()==null){
            return null;
        }
        String cartKey=CART_PREFIX+userInfoTo.getUserId();
        Map<String, String> cartItemMap = redisFeignService.getAllHash(cartKey);
        Collection<String> values = cartItemMap.values();
        //获取所有被选中的购物项
        List<CartItem> cartItems = values.stream().map(i -> {
            Gson gson = new Gson();
            CartItem cartItem = gson.fromJson( i, CartItem.class);
            return cartItem;
        }).filter(obj->obj.getCheck())
                //获取最新的价格
                .map(item->{
                    BigDecimal price = productFeignService.getPrice(item.getSkuId());
                    item.setPrice(price);
                    return item;
                })
                .collect(Collectors.toList());
        return cartItems;
    }
}
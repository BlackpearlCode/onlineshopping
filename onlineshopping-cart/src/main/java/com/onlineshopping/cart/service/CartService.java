package com.onlineshopping.cart.service;



import com.onlineshopping.cart.vo.Cart;
import com.onlineshopping.cart.vo.CartItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CartService {
    //添加商品到购物车
    CartItem addCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    //获取购物车中某个购物项
    CartItem getCartItem(Long skuId);

    //获取当前用户购物车所有购物项
    Cart getAllCarts();

    //情况购物车
    void clearCart(String cartKey);

    //勾选购物项
    void checkItem(Long skuId, Integer checked);
    //修改购物项数量
    void countItem(Long skuId, Integer num);
    //删除购物项
    void deleteItem(Integer skuId);
    //获取当前用户购物车所有购物项
    List<CartItem> getCurrentUserCartItems();
}

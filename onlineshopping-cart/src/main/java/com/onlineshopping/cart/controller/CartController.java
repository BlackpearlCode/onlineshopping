package com.onlineshopping.cart.controller;


import com.onlineshopping.cart.service.CartService;
import com.onlineshopping.cart.vo.Cart;
import com.onlineshopping.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     *获取购物车列表
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(Model model){

        //获取购物车列表
        Cart cart =cartService.getAllCarts();
        model.addAttribute("cart",cart);
        return "cartList";
    }

    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping("/addCartItem")
    public String addCartItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {
        cartService.addCart(skuId,num);
        redirectAttributes.addAttribute("skuId",skuId);
        return "redirect:http://cart.onlineshopping.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model){
      //重定向到成功页面，再次查询购物车数据
        CartItem cartItem=cartService.getCartItem(skuId);
        model.addAttribute("cartItem",cartItem);
        return "success";
    }

    /**
     * 修改购物项
     * @param skuId
     * @param checked
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/checkItem")
    public String checkItem(@RequestParam(value = "skuId") Long skuId, @RequestParam(value = "checked") Integer checked) throws ExecutionException, InterruptedException {
        cartService.checkItem(skuId,checked);
        return "redirect:http://cart.onlineshopping.com/cart.html";
    }

    /**
     * 修改购物项数量
     * @param skuId
     * @param num
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/countItem")
    public String countItem(@RequestParam(value = "skuId") Long skuId, @RequestParam(value = "num") Integer num) throws ExecutionException, InterruptedException {
        cartService.countItem(skuId,num);
        return "redirect:http://cart.onlineshopping.com/cart.html";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Integer skuId){
        cartService.deleteItem(skuId);
        return "redirect:http://cart.onlineshopping.com/cart.html";
    }
    //获取当前用户的购物车列表
    @GetMapping("/currentUserCartItems")
    @ResponseBody
    public List<CartItem> getCurrentUserCartItems(){
        List<CartItem> currentUserCartItems = cartService.getCurrentUserCartItems();
        return currentUserCartItems;
    }
}

package com.onlineshopping.order.controller;

import com.onlineshopping.order.exception.NoStockException;
import com.onlineshopping.order.service.OrderService;
import com.onlineshopping.order.vo.OrderConfirmVo;
import com.onlineshopping.order.vo.OrderSubmitVo;
import com.onlineshopping.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    /**
     * 跳转到订单确认页面
     * @param model
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo=orderService.confirmOrder();
        model.addAttribute("confirmOrderData",confirmVo);
        return "confirm";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo submitVo, Model model, RedirectAttributes redirectAttributes){
        try{
            //创建订单
            SubmitOrderResponseVo responseVo=orderService.submitOrder(submitVo);
            if(responseVo.getCode()==0){
                //下单成功到支付选择页面
                model.addAttribute("submitOrderResp",responseVo);
                return "pay";
            }else {
                String msg = "下单失败";
                switch (responseVo.getCode()) {
                    case 1:
                        msg += "：订单信息过期，请刷新后再次提交";
                        break;
                    case 2:
                        msg += "：订单商品价格发生变化，请确认后再次提交";
                        break;
                    case 3:
                        msg += "：库存锁定失败，请确认后再次提交";
                        break;
                    default:
                        msg += "：下单失败，请联系管理员";
                        break;
                }
                redirectAttributes.addFlashAttribute("msg", responseVo);
                return "redirect:http://order.onlineshopping.com/toTrade";
            }
        }catch (Exception e){
            if(e instanceof NoStockException){
                String message=e.getMessage();
                redirectAttributes.addFlashAttribute("msg",message);
            }
            return "redirect:http://order.onlineshopping.com/toTrade";
        }
    }
}

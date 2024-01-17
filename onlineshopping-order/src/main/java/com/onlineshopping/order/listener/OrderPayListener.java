package com.onlineshopping.order.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.onlineshopping.order.config.AlipayTemplate;
import com.onlineshopping.order.service.OrderService;
import com.onlineshopping.order.vo.PayAsyncVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
public class OrderPayListener {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayTemplate alipayTemplate;

    @PostMapping("/payed/notify")
    public String handleAlipayed(PayAsyncVo vo,HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException, ParseException {
        // 只要收到支付宝的异步通知，返回 success 支付宝便不再通知
        // 获取支付宝POST过来反馈信息
        //TODO 需要验签
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            params.put(name, valueStr);
            log.info("name:{},value:{}", name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(),
                alipayTemplate.getCharset(), alipayTemplate.getSign_type()); //调用SDK验证签名

        if (signVerified) {
            log.info("签名验证成功...");
            //去修改订单状态
            String result = orderService.handlePayResult(vo);
            return result;
        } else {
            log.error("签名验证失败...");
            return "error";
        }
    }
}
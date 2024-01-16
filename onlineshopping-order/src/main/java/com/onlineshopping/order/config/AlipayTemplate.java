package com.onlineshopping.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.onlineshopping.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "alipay")

/**
 * 该配置类为阿里云电脑网站支付的沙箱环境，所有配置均需按照阿里云的沙箱环境配置； 沙箱环境官方文档地址：https://open.alipay.com/develop/sandbox/app
 */
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "9021000134607509";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQD1rvmuo7iQej/fHpblA55t5lqtOPUPwPuTjmp2jJ8HQIWdbiwZq7xAH4IbhzbsvQ/Vko/jJoqDtV8V+jFUO4r1RVZIELxZ41ydxeLciDfMKITSojqcdXa+mHLO5xeQGPmSDgsrCvHGg9dsKpffxw49TroiP4uCY33E2TAtRp7RbEREKq7TidNhlJL4WrAA5rER8y5HCaZv6J9mPmvR+9yowydvVm77MAyRtwo57/IXUYkZpfitZAKv2apTJxDvGCFmfRunYNbQfrbpd5XqWPk1JlL3qrJM8A5nf8IHwkoYZb2M/6SbuRxMwb5VyvZx80KF8R5DVp6S/1e13VqD3RY3AgMBAAECggEBAOQNkdMcQlxKPjO8T1Ex9MQLCay1rDT9bAliL0zv50ZyDBVAiUsi8NGM7dbjwoYqUSJss4ek5PqxRxw9a8DGrIxT2m12q3+83uHNKHKRN0oZaoGi+Uj2eE0fcaLBPEwUAsYipxdDR36AL11GhEfCnGMoSvszhSp5c+tft6qvXQkC5wYT4zEneXq387/Ewl5k1xYOTQzHGK9W3z/NeB4MJ/GfgaY3M986P6XCIU/yxmus3DXNL9jl5ITfgNqDUQp4BArcIrNzMl/lpTsrEW8Cjw3JEAKOpt9cwR+NdL+alr5Peco3aSbuwcSoPdILXduO7yno5dQnZ0x+Ew/YN+j2f1ECgYEA/6mEGD7fa4Nc9UBy1iQapLJHX0CaUfNZcf1nc6KftkNgovnUFJky43ZQvR7uY0q8UrA63Z7IvwU5JFHDqQly2VmvaX/N2X0YvCG+cHsXhdv2AkKJfjNVN4IdxxHScr+ymq8pjYIQ9G3zS56dmEpRtuoT/f58NTYS4+iv3lqJBosCgYEA9gIVc45RIYGukh0IysI64IECx0+sXhzvQ6r9OwNIS72Dw9ojza9VqcauffqYKInlDxRU56QoW8A5KdirV5zBcL1G1ZBbrZWvd2XH7DydUrml4lIB8pZNkSGzQ51S7w0yHyGIRn3AuoCcU6ZMJZOET/Vp1wz9GyZ7HqUthQtMEIUCgYEA6b4ERV6GSfeqL5gi5eHQci+EzXN1I+PHLU6vHKvcNXlYiui6m7KNMiVkH+R9s04K69avmxunEe4mliwjJRK8bDdxBpWQkH2s9aQsRw5jLUEzpFFM/5LTIJLXwr9qlvrowj/PT89QzQkRXVvBXhDksMVGTfwzdzre/nwo/rPrf40CgYALVhU++fPrI1q0CPp6vXCRH+DM5nL+8z5jU+HPcltGgjksMUUtGUNNS4aG76FLXO9dPoabFgl2EOmRcBaVNvgDBfXZqYlP+nHXgq0vyatGQJ/QSg8k6qAwa+ki2IEV2vnQTW9GME1NStfpUuR3ZtDWDm0OJ98R/Tf3NY1fcCHVMQKBgQDCgEgMoK+GOHNy0yFqJBRcCWe3H46gGRuVKUn9XHU2Om4PNpNJCuUlnPoH9JkaYZZx7QhQFT67Hv4NHF/cqRN33z/NDe4LVs03rx8gh8FRXJKAYtf3nu7tuHvMSvEG+9RBNJv3BdBvup1IivFeBl3U6R7i3sINMxT1dS+htCFnQw==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtI0T7aVQzaD1rjAPLBlhMaAZrUQ5HI1hjFNIcCqP8ZH1fIHonJUfDQbZvZgWGMt6vMcEJTvGKK5+APZ/eWHDPIFydJ8EK3KCk0yc+rmV0y9qHCsNWIwkXP/Nr7kaf54fz4UniJdYqtSqVNCi8TGKhYRwCKLZuxxSY21E7FrpKZhqwKNI+TN6jWLaC6p+xZjgOZ70SDXeNw3+sYkvBJiy34SIK5G9s0vGrSZ3B+N+H7/AA66uoHOEAiYpxR0v1iM3P/Ptt1GMV6XKl1b7dIyi3i6lTXsmHjV/L9Z8YmmBUd4ICPtB+DXV8I2RI5yIe9/RToDaM9pkEn/OYzZIJX4Q3wIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url="http://onlineshopping.free.idcfengye.com /order/pay/alipay/success";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url="http://member.onlineshopping.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}

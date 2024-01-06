package com.onlineshopping.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单项信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * order_id
    */
    private Long orderId;

    /**
    * order_sn 订单号
    */
    private String orderSn;

    /**
    * spu_id
    */
    private Long spuId;

    /**
    * spu_name
    */
    private String spuName;

    /**
    * spu_pic 商品种类图片
    */
    private String spuPic;

    /**
    * 品牌
    */
    private String spuBrand;

    /**
    * 商品分类id
    */
    private Long categoryId;

    /**
    * 商品sku编号
    */
    private Long skuId;

    /**
    * 商品sku名字
    */
    private String skuName;

    /**
    * 商品sku图片
    */
    private String skuPic;

    /**
    * 商品sku价格
    */
    private BigDecimal skuPrice;

    /**
    * 商品购买的数量
    */
    private Integer skuQuantity;

    /**
    * 商品销售属性组合（JSON）
    */
    private String skuAttrsVals;

    /**
    * 商品促销分解金额
    */
    private BigDecimal promotionAmount;

    /**
    * 优惠券优惠分解金额
    */
    private BigDecimal couponAmount;

    /**
    * 积分优惠分解金额
    */
    private BigDecimal integrationAmount;

    /**
    * 该商品经过优惠后的分解金额
    */
    private BigDecimal realAmount;

    /**
    * 赠送积分
    */
    private Integer giftIntegration;

    /**
    * 赠送成长值
    */
    private Integer giftGrowth;

    private static final long serialVersionUID = 1L;
}
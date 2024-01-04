package com.onlineshopping.cart.vo;


import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车内容
 */

public class Cart {
    List<CartItem> items;
    //商品数量
    private Integer countNum;
    //商品类型
    private Integer countType;
    //商品总价
    private BigDecimal totalAmount;
    //减免价格
    private BigDecimal reduce=new BigDecimal("0");

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count=0;
        if(CollectionUtils.isEmpty(items)){
            for(CartItem item:items){
                count+=item.getCount();
            };
        }
        return count;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public Integer getCountType() {
        int count=0;
        if(CollectionUtils.isEmpty(items)){
            for(CartItem item:items){
                count+=1;
            };
        }
        return count;
    }


    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        //计算购物项总价
        if(!CollectionUtils.isEmpty(items)){
            for(CartItem item:items){
                if(item.getCheck()){
                    BigDecimal totalPrice = item.getTotalPrice();
                    amount=amount.add(totalPrice);
                }
            };
        }
        //减去优惠总价
        BigDecimal subtract = amount.subtract(getReduce());
        return subtract;
    }



    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}

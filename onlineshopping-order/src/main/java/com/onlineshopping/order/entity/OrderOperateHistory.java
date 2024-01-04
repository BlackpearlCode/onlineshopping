package com.onlineshopping.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单操作历史记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOperateHistory implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 订单id
    */
    private Long orderId;

    /**
    * 操作人[用户；系统；后台管理员]
    */
    private String operateMan;

    /**
    * 操作时间
    */
    private Date createTime;

    /**
    * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
    */
    private Byte orderStatus;

    /**
    * 备注
    */
    private String note;

    private static final long serialVersionUID = 1L;
}
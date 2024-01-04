package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 秒杀活动
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillPromotion implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 活动标题
    */
    private String title;

    /**
    * 开始日期
    */
    private Date startTime;

    /**
    * 结束日期
    */
    private Date endTime;

    /**
    * 上下线状态
    */
    private Byte status;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 创建人
    */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
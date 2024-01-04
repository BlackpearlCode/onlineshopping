package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 秒杀活动场次
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillSession implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 场次名称
    */
    private String name;

    /**
    * 每日开始时间
    */
    private Date startTime;

    /**
    * 每日结束时间
    */
    private Date endTime;

    /**
    * 启用状态
    */
    private Boolean status;

    /**
    * 创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
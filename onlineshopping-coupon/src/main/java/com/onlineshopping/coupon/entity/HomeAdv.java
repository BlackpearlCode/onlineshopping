package com.onlineshopping.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 首页轮播广告
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeAdv implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 名字
    */
    private String name;

    /**
    * 图片地址
    */
    private String pic;

    /**
    * 开始时间
    */
    private Date startTime;

    /**
    * 结束时间
    */
    private Date endTime;

    /**
    * 状态
    */
    private Boolean status;

    /**
    * 点击数
    */
    private Integer clickCount;

    /**
    * 广告详情连接地址
    */
    private String url;

    /**
    * 备注
    */
    private String note;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 发布者
    */
    private Long publisherId;

    /**
    * 审核者
    */
    private Long authId;

    private static final long serialVersionUID = 1L;
}
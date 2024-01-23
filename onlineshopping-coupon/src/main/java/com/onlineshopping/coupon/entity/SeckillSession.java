package com.onlineshopping.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    /**
     * 该场次活动关联的商品
     */
    @TableField(exist = false)
    private List<SeckillSkuRelation> relationSkus;

    private static final long serialVersionUID = 1L;
}
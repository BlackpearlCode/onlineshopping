package com.onlineshopping.seckill.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SeckillSessionsWithSkus {
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



    private List<SeckillSkuVo> relationSkus;
}

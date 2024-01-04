package com.onlineshopping.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 退货原因
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderReturnReason implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 退货原因名
    */
    private String name;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 启用状态
    */
    private Boolean status;

    /**
    * create_time
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
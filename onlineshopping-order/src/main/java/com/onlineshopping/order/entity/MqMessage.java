package com.onlineshopping.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqMessage implements Serializable {
    private String messageId;

    private String content;

    private String toExchane;

    private String routingKey;

    private String classType;

    /**
    * 0-新建 1-已发送 2-错误抵达 3-已抵达
    */
    private Integer messageStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
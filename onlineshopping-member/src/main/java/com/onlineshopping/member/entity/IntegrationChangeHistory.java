package com.onlineshopping.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 积分变化历史记录
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationChangeHistory implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * member_id
    */
    private Long memberId;

    /**
    * create_time
    */
    private Date createTime;

    /**
    * 变化的值
    */
    private Integer changeCount;

    /**
    * 备注
    */
    private String note;

    /**
    * 来源[0->购物；1->管理员修改;2->活动]
    */
    private Byte sourceTyoe;

    private static final long serialVersionUID = 1L;
}
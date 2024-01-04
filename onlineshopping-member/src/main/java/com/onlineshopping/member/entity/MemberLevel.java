package com.onlineshopping.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
    * 会员等级
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLevel implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 等级名称
    */
    private String name;

    /**
    * 等级需要的成长值
    */
    private Integer growthPoint;

    /**
    * 是否为默认等级[0->不是；1->是]
    */
    private Byte defaultStatus;

    /**
    * 免运费标准
    */
    private BigDecimal freeFreightPoint;

    /**
    * 每次评价获取的成长值
    */
    private Integer commentGrowthPoint;

    /**
    * 是否有免邮特权
    */
    private Byte priviledgeFreeFreight;

    /**
    * 是否有会员价格特权
    */
    private Byte priviledgeMemberPrice;

    /**
    * 是否有生日特权
    */
    private Byte priviledgeBirthday;

    /**
    * 备注
    */
    private String note;

    private static final long serialVersionUID = 1L;


}
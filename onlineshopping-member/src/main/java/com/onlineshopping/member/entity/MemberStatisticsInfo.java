package com.onlineshopping.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
    * 会员统计信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatisticsInfo implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 会员id
    */
    private Long memberId;

    /**
    * 累计消费金额
    */
    private BigDecimal consumeAmount;

    /**
    * 累计优惠金额
    */
    private BigDecimal couponAmount;

    /**
    * 订单数量
    */
    private Integer orderCount;

    /**
    * 优惠券数量
    */
    private Integer couponCount;

    /**
    * 评价数
    */
    private Integer commentCount;

    /**
    * 退货数量
    */
    private Integer returnOrderCount;

    /**
    * 登录次数
    */
    private Integer loginCount;

    /**
    * 关注数量
    */
    private Integer attendCount;

    /**
    * 粉丝数量
    */
    private Integer fansCount;

    /**
    * 收藏的商品数量
    */
    private Integer collectProductCount;

    /**
    * 收藏的专题活动数量
    */
    private Integer collectSubjectCount;

    /**
    * 收藏的评论数量
    */
    private Integer collectCommentCount;

    /**
    * 邀请的朋友数量
    */
    private Integer inviteFriendCount;

    private static final long serialVersionUID = 1L;
}
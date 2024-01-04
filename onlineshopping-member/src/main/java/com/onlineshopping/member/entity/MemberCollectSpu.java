package com.onlineshopping.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 会员收藏的商品
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCollectSpu implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 会员id
    */
    private Long memberId;

    /**
    * spu_id
    */
    private Long spuId;

    /**
    * spu_name
    */
    private String spuName;

    /**
    * spu_img
    */
    private String spuImg;

    /**
    * create_time
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
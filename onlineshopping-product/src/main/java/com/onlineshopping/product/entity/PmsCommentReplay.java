package com.onlineshopping.product.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 商品评价回复关系
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsCommentReplay implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 评论id
    */
    private Long commentId;

    /**
    * 回复id
    */
    private Long replyId;

    private static final long serialVersionUID = 1L;
}
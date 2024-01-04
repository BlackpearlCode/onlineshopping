package com.onlineshopping.product.entity;

import java.io.Serializable;


//import com.onlineshopping.common.valid.AddGroup;
//import com.onlineshopping.common.valid.ListValue;
//import com.onlineshopping.common.valid.UpdateGroup;
//import com.onlineshopping.common.valid.UpdateStatusGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.URL;


/**
    * 品牌
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsBrand implements Serializable {
    /**
    * 品牌id
    */
//    @Null(message = "修改必须指定品牌id",groups = {UpdateGroup.class})
//    @NotNull(message = "新增不能指定品牌id",groups = {AddGroup.class})
    private Long brandId;

    /**
    * 品牌名
    */
    //@NotBlank(message = "品牌名不能为空",groups = {UpdateGroup.class,AddGroup.class})
    private String name;

    /**
    * 品牌logo地址
    */
    //@NotBlank(message = "logo不能为空",groups = {UpdateGroup.class,AddGroup.class})
    //@URL(message = "logo必须是一个合法的url地址")
    private String logo;

    /**
    * 介绍
    */
    private String descript;

    /**
    * 显示状态[0-不显示；1-显示]
    */
    //@NotNull(groups = {UpdateGroup.class,AddGroup.class, UpdateStatusGroup.class},message = "showStatus不能为null")
    //@ListValue(values={0,1},groups = {UpdateGroup.class,AddGroup.class, UpdateStatusGroup.class} )
    private Integer showStatus;

    /**
    * 检索首字母
    */
    //@NotEmpty(groups = {AddGroup.class})
    //@Pattern(regexp="^[a-zA-Z]$",message = "检索首字母必须是一个字母",groups = {UpdateGroup.class,AddGroup.class})
    private String firstLetter;

    /**
    * 排序
    */
    //@NotNull(groups = {AddGroup.class})
    //@Min(value = 0,message = "排序必须大于等于0",groups = {UpdateGroup.class,AddGroup.class})
    private Integer sort;

    private static final long serialVersionUID = 1L;


}
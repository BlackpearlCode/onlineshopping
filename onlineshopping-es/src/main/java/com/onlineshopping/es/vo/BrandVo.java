package com.onlineshopping.es.vo;

import lombok.Data;

@Data
public class BrandVo {

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


}

package com.onlineshopping.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catelog2Vo {
    //一级分类id
    private String catelog1Id;
    //三级子分类
    private List<Catelog3Vo> catalog3list;
    private String id;
    private String name;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catelog3Vo{
        //二级分类id
        private String catelog2Id;
        private String id;
        private String name;
    }
}

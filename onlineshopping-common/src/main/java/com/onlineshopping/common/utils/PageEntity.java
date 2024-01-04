package com.onlineshopping.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageEntity<T> implements Serializable {
    //总条数
    private Long total;
    //总页码
    private Integer page;
    //当前页的记录
    private List<T> pageList;



}

package com.onlineshopping.ware.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeVo {
    //整单Id
    private Long purchaseId;
    //合并采购单集合
    private List<Long> items;
}

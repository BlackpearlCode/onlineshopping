package com.onlineshopping.ware.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemDoneVo {

    //采购项id
    private Long itemId;
    //采购状态
    private Integer status;
    //采购原因
    private String reason;
}

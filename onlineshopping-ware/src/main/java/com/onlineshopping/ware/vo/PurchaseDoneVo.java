package com.onlineshopping.ware.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDoneVo {
    //采购单id
    @NotNull
    private Long id;
    //采购项信息
    private List<PurchaseItemDoneVo> items;
}

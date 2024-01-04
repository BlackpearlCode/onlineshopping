package com.onlineshopping.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListForspuIdVo {
    private String attrName;
    private String attrValue;
    private Byte quickShow;
    private Long attrId;
}

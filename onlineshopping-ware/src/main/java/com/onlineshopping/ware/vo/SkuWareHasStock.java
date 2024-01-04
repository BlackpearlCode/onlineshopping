package com.onlineshopping.ware.vo;

import lombok.Data;

import java.util.List;

/*8
 * @Description: 商品库存
 */
@Data
public class SkuWareHasStock {
    //商品id
    private Long skuId;
    //那个仓库有该商品的库存的库存Id
    private List<Long> wareId;
    //商品锁定数量
    private Integer num;
}

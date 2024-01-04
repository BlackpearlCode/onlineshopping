package com.onlineshopping.product.service;



import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsBrand;


import java.util.List;


public interface PmsBrandService  {


    int deleteByPrimaryKey(Long brandId);

    int insert(PmsBrand record);

    int insertSelective(PmsBrand record);

    PmsBrand selectByPrimaryKey(Long brandId);

    int updateByPrimaryKeySelective(PmsBrand record);

    int updateByPrimaryKey(PmsBrand record);

    PageEntity selectAll(int page, int limit);

    //根据品牌id批量删除数据
    int batchDelete(List<Long> brandIds);

    //通过品牌名条件查询
    PageEntity selectByName(int page, int limit,String name);


    List<PmsBrand> getBrands(List<Long> ids);
}

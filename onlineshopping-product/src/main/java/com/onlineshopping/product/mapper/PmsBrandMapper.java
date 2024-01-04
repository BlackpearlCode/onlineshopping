package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsBrandMapper {
    int deleteByPrimaryKey(Long brandId);

    int insert(PmsBrand record);

    int insertSelective(PmsBrand record);

    PmsBrand selectByPrimaryKey(Long brandId);

    int updateByPrimaryKeySelective(PmsBrand record);

    int updateByPrimaryKey(PmsBrand record);

    List<PmsBrand> selectAll();

    int BatchDelete(List<Long> brandIds);

    List<PmsBrand> selectByName(String name);


    List<PmsBrand> getBrands(@Param("ids") List<Long> ids);
}
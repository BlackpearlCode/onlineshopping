package com.onlineshopping.product.mapper;

import com.onlineshopping.product.entity.PmsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PmsCategoryMapper {
    int deleteByPrimaryKey(Long catId);

    int insert(PmsCategory record);

    int insertSelective(PmsCategory record);

    PmsCategory selectByPrimaryKey(Long catId);

    int updateByPrimaryKeySelective(PmsCategory record);

    int updateByPrimaryKey(PmsCategory record);

    List<PmsCategory> list();

    int updateShowStatusList(@Param("list") List<Long> catIds);


    List<PmsCategory> selectByParentId(Long parentId);
    List<PmsCategory> selectAll();
}
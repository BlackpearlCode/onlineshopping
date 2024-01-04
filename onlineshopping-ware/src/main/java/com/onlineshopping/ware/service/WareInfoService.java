package com.onlineshopping.ware.service;

import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.ware.entity.WareInfo;
import com.onlineshopping.ware.vo.FareVo;

import java.util.List;
import java.util.Map;

public interface WareInfoService{


    int deleteByPrimaryKey(Long id);

    int insert(WareInfo record);

    int insertSelective(WareInfo record);

    WareInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WareInfo record);

    int updateByPrimaryKey(WareInfo record);

    PageEntity getAll(Map<String, Object> params);

    void batchDeleteById(List<Long> ids);

    //根据用户地址进行收取运费
    FareVo getFare(Long id);
}

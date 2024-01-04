package com.onlineshopping.product.service.impl;

import com.onlineshopping.product.entity.PmsSpuInfoDesc;
import com.onlineshopping.product.mapper.PmsSpuInfoDescMapper;
import com.onlineshopping.product.service.PmsSpuInfoDescService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class PmsSpuInfoDescServiceImpl implements PmsSpuInfoDescService {

    @Resource
    private PmsSpuInfoDescMapper pmsSpuInfoDescMapper;

    @Override
    public int deleteByPrimaryKey(Long spuId) {
        return pmsSpuInfoDescMapper.deleteByPrimaryKey(spuId);
    }

    @Override
    public int insert(PmsSpuInfoDesc record) {
        return pmsSpuInfoDescMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsSpuInfoDesc record) {
        return pmsSpuInfoDescMapper.insertSelective(record);
    }

    @Override
    public PmsSpuInfoDesc selectByPrimaryKey(Long spuId) {
        return pmsSpuInfoDescMapper.selectByPrimaryKey(spuId);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsSpuInfoDesc record) {
        return pmsSpuInfoDescMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsSpuInfoDesc record) {
        return pmsSpuInfoDescMapper.updateByPrimaryKey(record);
    }

}

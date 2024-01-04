package com.onlineshopping.product.service.impl;
import com.onlineshopping.product.entity.PmsSkuSaleAttrValue;
import com.onlineshopping.product.mapper.PmsAttrAttrgroupRelationMapper;
import com.onlineshopping.product.mapper.PmsAttrGroupMapper;
import com.onlineshopping.product.mapper.PmsAttrMapper;
import com.onlineshopping.product.mapper.PmsSkuSaleAttrValueMapper;
import com.onlineshopping.product.service.PmsSkuSaleAttrValueService;
import com.onlineshopping.product.vo.SkuItemSaleAttrVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsSkuSaleAttrValueServiceImpl implements PmsSkuSaleAttrValueService {

    @Resource
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Resource
    private PmsAttrGroupMapper pmsAttrGroupMapper;
    @Resource
    private PmsAttrAttrgroupRelationMapper attrAttrgroupRelationMapper;
    @Resource
    private PmsAttrMapper attrMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return pmsSkuSaleAttrValueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PmsSkuSaleAttrValue record) {
        return pmsSkuSaleAttrValueMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsSkuSaleAttrValue record) {
        return pmsSkuSaleAttrValueMapper.insertSelective(record);
    }

    @Override
    public PmsSkuSaleAttrValue selectByPrimaryKey(Long id) {
        return pmsSkuSaleAttrValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsSkuSaleAttrValue record) {
        return pmsSkuSaleAttrValueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsSkuSaleAttrValue record) {
        return pmsSkuSaleAttrValueMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId) {
        List<SkuItemSaleAttrVo> saleAttrVos=pmsSkuSaleAttrValueMapper.getSaleAttrsBySpuId(spuId);
        return saleAttrVos;
    }

    @Override
    public List<String> getSkuSaleAttrValuesAsStringList(Long skuId) {
       return pmsSkuSaleAttrValueMapper.getSkuSaleAttrValuesAsStringList(skuId);
    }

}

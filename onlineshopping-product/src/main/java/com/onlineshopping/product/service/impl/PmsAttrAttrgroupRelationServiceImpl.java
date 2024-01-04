package com.onlineshopping.product.service.impl;

import com.onlineshopping.product.entity.PmsAttrAttrgroupRelation;
import com.onlineshopping.product.mapper.PmsAttrAttrgroupRelationMapper;
import com.onlineshopping.product.service.PmsAttrAttrgroupRelationService;
import com.onlineshopping.product.vo.AttrGroupRelationVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PmsAttrAttrgroupRelationServiceImpl implements PmsAttrAttrgroupRelationService {

    @Resource
    private PmsAttrAttrgroupRelationMapper pmsAttrAttrgroupRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return pmsAttrAttrgroupRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PmsAttrAttrgroupRelation record) {
        return pmsAttrAttrgroupRelationMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsAttrAttrgroupRelation record) {
        return pmsAttrAttrgroupRelationMapper.insertSelective(record);
    }

    @Override
    public PmsAttrAttrgroupRelation selectByPrimaryKey(Long id) {
        return pmsAttrAttrgroupRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsAttrAttrgroupRelation record) {
        return pmsAttrAttrgroupRelationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsAttrAttrgroupRelation record) {
        return pmsAttrAttrgroupRelationMapper.updateByPrimaryKey(record);
    }


    public void updateByAttrId(Long attrGroupId, Long attrId) {
        pmsAttrAttrgroupRelationMapper.updateByAttrId(attrGroupId,attrId);
    }

    @Override
    public List<Long> selectByGroupId(Long attrGroupId) {
        return pmsAttrAttrgroupRelationMapper.selectByGroupId(attrGroupId);
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] relationVo) {
        pmsAttrAttrgroupRelationMapper.deleteBatch(relationVo);
    }

    @Override
    public void batchInsert(List<PmsAttrAttrgroupRelation> relationVo) {
        pmsAttrAttrgroupRelationMapper.batchInsert(relationVo);
    }
}

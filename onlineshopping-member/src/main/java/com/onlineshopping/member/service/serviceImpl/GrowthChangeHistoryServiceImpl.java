package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.GrowthChangeHistory;
import com.onlineshopping.member.mapper.GrowthChangeHistoryMapper;
import com.onlineshopping.member.service.GrowthChangeHistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class GrowthChangeHistoryServiceImpl implements GrowthChangeHistoryService{

    @Resource
    private GrowthChangeHistoryMapper growthChangeHistoryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return growthChangeHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(GrowthChangeHistory record) {
        return growthChangeHistoryMapper.insert(record);
    }

    @Override
    public int insertSelective(GrowthChangeHistory record) {
        return growthChangeHistoryMapper.insertSelective(record);
    }

    @Override
    public GrowthChangeHistory selectByPrimaryKey(Long id) {
        return growthChangeHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(GrowthChangeHistory record) {
        return growthChangeHistoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(GrowthChangeHistory record) {
        return growthChangeHistoryMapper.updateByPrimaryKey(record);
    }

}

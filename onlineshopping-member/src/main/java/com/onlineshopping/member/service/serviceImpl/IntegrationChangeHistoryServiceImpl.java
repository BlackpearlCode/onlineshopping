package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.IntegrationChangeHistory;
import com.onlineshopping.member.mapper.IntegrationChangeHistoryMapper;
import com.onlineshopping.member.service.IntegrationChangeHistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class IntegrationChangeHistoryServiceImpl implements IntegrationChangeHistoryService{

    @Resource
    private IntegrationChangeHistoryMapper integrationChangeHistoryMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return integrationChangeHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IntegrationChangeHistory record) {
        return integrationChangeHistoryMapper.insert(record);
    }

    @Override
    public int insertSelective(IntegrationChangeHistory record) {
        return integrationChangeHistoryMapper.insertSelective(record);
    }

    @Override
    public IntegrationChangeHistory selectByPrimaryKey(Long id) {
        return integrationChangeHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(IntegrationChangeHistory record) {
        return integrationChangeHistoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IntegrationChangeHistory record) {
        return integrationChangeHistoryMapper.updateByPrimaryKey(record);
    }

}

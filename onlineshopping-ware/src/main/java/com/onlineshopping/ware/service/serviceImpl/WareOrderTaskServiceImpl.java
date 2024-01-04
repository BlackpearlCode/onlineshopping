package com.onlineshopping.ware.service.serviceImpl;

import com.onlineshopping.ware.entity.WareOrderTask;
import com.onlineshopping.ware.mapper.WareOrderTaskMapper;
import com.onlineshopping.ware.service.WareOrderTaskService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class WareOrderTaskServiceImpl implements WareOrderTaskService{

    @Resource
    private WareOrderTaskMapper wareOrderTaskMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return wareOrderTaskMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WareOrderTask record) {
        return wareOrderTaskMapper.insert(record);
    }

    @Override
    public int insertSelective(WareOrderTask record) {
        return wareOrderTaskMapper.insertSelective(record);
    }

    @Override
    public WareOrderTask selectByPrimaryKey(Long id) {
        return wareOrderTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WareOrderTask record) {
        return wareOrderTaskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WareOrderTask record) {
        return wareOrderTaskMapper.updateByPrimaryKey(record);
    }

}

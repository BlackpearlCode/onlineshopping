package com.onlineshopping.ware.service.serviceImpl;

import com.onlineshopping.ware.entity.WareOrderTaskDetail;
import com.onlineshopping.ware.mapper.WareOrderTaskDetailMapper;
import com.onlineshopping.ware.service.WareOrderTaskDetailService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareOrderTaskDetailServiceImpl implements WareOrderTaskDetailService{

    @Resource
    private WareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return wareOrderTaskDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WareOrderTaskDetail record) {
        return wareOrderTaskDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(WareOrderTaskDetail record) {
        return wareOrderTaskDetailMapper.insertSelective(record);
    }

    @Override
    public WareOrderTaskDetail selectByPrimaryKey(Long id) {
        return wareOrderTaskDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WareOrderTaskDetail record) {
        return wareOrderTaskDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WareOrderTaskDetail record) {
        return wareOrderTaskDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public void save(WareOrderTaskDetail wareOrderTaskDetail) {
        wareOrderTaskDetailMapper.insertSelective(wareOrderTaskDetail);
    }

    @Override
    public List<WareOrderTaskDetail> getOrderTaskDetailByTaskId(Long id) {
        return wareOrderTaskDetailMapper.getOrderTaskDetailByTaskId(id);
    }

}

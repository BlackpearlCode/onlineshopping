package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.UndoLog;
import com.onlineshopping.coupon.mapper.UndoLogMapper;
import com.onlineshopping.coupon.service.UndoLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class UndoLogServiceImpl implements UndoLogService{

    @Resource
    private UndoLogMapper undoLogMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return undoLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UndoLog record) {
        return undoLogMapper.insert(record);
    }

    @Override
    public int insertSelective(UndoLog record) {
        return undoLogMapper.insertSelective(record);
    }

    @Override
    public UndoLog selectByPrimaryKey(Long id) {
        return undoLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UndoLog record) {
        return undoLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UndoLog record) {
        return undoLogMapper.updateByPrimaryKey(record);
    }

}

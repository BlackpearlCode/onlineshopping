package com.onlineshopping.order.service.serviceImpl;

import com.onlineshopping.order.entity.UndoLog;
import com.onlineshopping.order.mapper.UndoLogMapper;
import com.onlineshopping.order.service.UndoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UndoLogServiceImpl implements UndoLogService {

    @Autowired
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

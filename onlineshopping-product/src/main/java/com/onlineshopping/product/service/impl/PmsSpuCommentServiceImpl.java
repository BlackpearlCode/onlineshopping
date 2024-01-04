package com.onlineshopping.product.service.impl;

import com.onlineshopping.product.entity.PmsSpuComment;
import com.onlineshopping.product.mapper.PmsSpuCommentMapper;
import com.onlineshopping.product.service.PmsSpuCommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class PmsSpuCommentServiceImpl implements PmsSpuCommentService {

    @Resource
    private PmsSpuCommentMapper pmsSpuCommentMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return pmsSpuCommentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PmsSpuComment record) {
        return pmsSpuCommentMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsSpuComment record) {
        return pmsSpuCommentMapper.insertSelective(record);
    }

    @Override
    public PmsSpuComment selectByPrimaryKey(Long id) {
        return pmsSpuCommentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsSpuComment record) {
        return pmsSpuCommentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsSpuComment record) {
        return pmsSpuCommentMapper.updateByPrimaryKey(record);
    }

}

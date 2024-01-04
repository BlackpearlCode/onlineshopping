package com.onlineshopping.ware.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.ware.entity.PurchaseDetail;
import com.onlineshopping.ware.mapper.PurchaseDetailMapper;
import com.onlineshopping.ware.service.PurchaseDetailService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService{

    @Resource
    private PurchaseDetailMapper purchaseDetailMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return purchaseDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PurchaseDetail record) {
        return purchaseDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(PurchaseDetail record) {
        return purchaseDetailMapper.insertSelective(record);
    }

    @Override
    public PurchaseDetail selectByPrimaryKey(Long id) {
        return purchaseDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PurchaseDetail record) {
        return purchaseDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PurchaseDetail record) {
        return purchaseDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity getAll(Map<String, Object> params) {

        List<PurchaseDetail> purchaseDetailList=purchaseDetailMapper.getAll(params);
        PageHelper.startPage(Integer.parseInt((String) params.get("page")),Integer.parseInt((String) params.get("limit")));
        PageInfo<PurchaseDetail> pageInfo=new PageInfo<>(purchaseDetailList);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
        return pageEntity;
    }

    @Override
    public void batchDelete(List<Long> ids) {
        purchaseDetailMapper.batchDelete(ids);
    }

}

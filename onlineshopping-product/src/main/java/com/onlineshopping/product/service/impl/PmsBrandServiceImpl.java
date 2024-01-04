package com.onlineshopping.product.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsBrand;
import com.onlineshopping.product.mapper.PmsBrandMapper;
import com.onlineshopping.product.service.PmsBrandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Resource
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public int deleteByPrimaryKey(Long brandId) {
        return pmsBrandMapper.deleteByPrimaryKey(brandId);
    }

    @Override
    public int insert(PmsBrand record) {
        return pmsBrandMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsBrand record) {
        return pmsBrandMapper.insertSelective(record);
    }

    @Override
    public PmsBrand selectByPrimaryKey(Long brandId) {
        return pmsBrandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsBrand record) {
        return pmsBrandMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsBrand record) {
        return pmsBrandMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity selectAll(int page, int limit) {
        PageHelper.startPage(page,limit);
        List<PmsBrand> brandList=pmsBrandMapper.selectAll();
        PageInfo<PmsBrand> pageInfo=new PageInfo<>(brandList);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(),pageInfo.getList());

        return pageEntity;
    }

    @Override
    public int batchDelete(List<Long> brandIds) {
        return pmsBrandMapper.BatchDelete(brandIds);
    }

    @Override
    public PageEntity selectByName(int page, int limit,String name) {
        PageHelper.startPage(page,limit);
        List<PmsBrand> brandList=pmsBrandMapper.selectByName(name);
        PageInfo<PmsBrand> pageInfo=new PageInfo<>(brandList);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(),pageInfo.getList());

        return pageEntity;
    }

    @Override
    public List<PmsBrand> getBrands(List<Long> ids) {
        return pmsBrandMapper.getBrands(ids);
    }


}

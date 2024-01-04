package com.onlineshopping.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.product.entity.PmsAttr;
import com.onlineshopping.product.entity.PmsAttrGroup;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.PmsCategory;
import com.onlineshopping.product.mapper.PmsAttrAttrgroupRelationMapper;
import com.onlineshopping.product.mapper.PmsAttrMapper;
import com.onlineshopping.product.mapper.PmsCategoryMapper;
import com.onlineshopping.product.service.PmsAttrGroupService;
import com.onlineshopping.product.mapper.PmsAttrGroupMapper;
import com.onlineshopping.product.vo.AttrGroupWithAttrsVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PmsAttrGroupServiceImpl implements PmsAttrGroupService {

    @Resource
    private PmsAttrGroupMapper pmsAttrGroupMapper;

    @Resource
    private PmsCategoryMapper pmsCategoryMapper;

    @Resource
    private PmsAttrMapper pmsAttrMapper;

    @Resource
    private PmsAttrAttrgroupRelationMapper pmsAttrAttrgroupRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long attrGroupId) {
        return pmsAttrGroupMapper.deleteByPrimaryKey(attrGroupId);
    }

    @Override
    public int insert(PmsAttrGroup record) {
        return pmsAttrGroupMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsAttrGroup record) {
        return pmsAttrGroupMapper.insertSelective(record);
    }

    @Override
    public PmsAttrGroup selectByPrimaryKey(Long attrGroupId) {
        return pmsAttrGroupMapper.selectByPrimaryKey(attrGroupId);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsAttrGroup record) {
        return pmsAttrGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsAttrGroup record) {
        return pmsAttrGroupMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity selectAll(long catelogId,int page, int limit, String key) {
        List<PmsAttrGroup> attrGroupList ;

        //如果catelogId==0，查询所有属性分组信息
        if(0==catelogId){
            attrGroupList=pmsAttrGroupMapper.selectAll(key);
            PageHelper.startPage(page,limit);
            PageInfo<PmsAttrGroup> pageInfo=new PageInfo<>(attrGroupList);
            PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
            return pageEntity;
        }
        //如果catelogId不等于0，根据catelog_id==catelogId and att_group_id=key条件查询或catelog_id==catelogId and att_group_name like '%key%'模糊查询
        attrGroupList= pmsAttrGroupMapper.selectByAttrGroupNameOrId(key,catelogId);
        PageHelper.startPage(page,limit);
        PageInfo<PmsAttrGroup> pageInfo=new PageInfo<>(attrGroupList);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
        return pageEntity;
    }

    @Override
    public int batchDelete(Long[] attrGroupIds) {
        List<Long> attrGroupIdList= Arrays.asList(attrGroupIds);
        return pmsAttrGroupMapper.batchDelete(attrGroupIdList);
    }

    @Override
    public Long[] catelogPath(long catId) {
        List<Long> pathList=new LinkedList<>();
        PmsCategory category;
        category= pmsCategoryMapper.selectByPrimaryKey(catId);
        Long[] path;
        pathList.add(catId);
        if(category.getParentCid()==0){
            path= pathList.toArray(new Long[pathList.size()]);
            return path;
        }else{
            while(category.getParentCid()!=0){
                category= pmsCategoryMapper.selectByPrimaryKey(category.getParentCid());
                pathList.add(category.getCatId());
            }
            Collections.reverse(pathList);
            path= pathList.toArray(new Long[pathList.size()]);
            return path;
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //1.根据分类id查询所属分组信息;
        List<PmsAttrGroup> attrGroups = pmsAttrGroupMapper.selectByCatelogId(catelogId);
        //根据所属id查询对应的属性信息存入map集合，key：groupID,value：对应的属性信息
        List<AttrGroupWithAttrsVo> attrGroupWithAttrsVos=attrGroups.stream().map(item->{
            AttrGroupWithAttrsVo vo=new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(item,vo);
            //通过attrGroupId查询attrId
            List<Long> attrIds = pmsAttrAttrgroupRelationMapper.selectByGroupId(item.getAttrGroupId());
            //通过attrId查询对应的属性信息
            List<PmsAttr> attrList=attrIds.stream().map(attr->{
                return pmsAttrMapper.selectByPrimaryKey(Long.valueOf(attr));
            }).collect(Collectors.toList());
            vo.setAttrs(attrList);
            return vo;
        }).collect(Collectors.toList());

        return attrGroupWithAttrsVos;
    }

}

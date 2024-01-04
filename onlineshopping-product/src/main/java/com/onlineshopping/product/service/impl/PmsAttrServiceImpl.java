package com.onlineshopping.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineshopping.common.constant.ProductConstant;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.product.entity.*;
import com.onlineshopping.product.mapper.PmsAttrAttrgroupRelationMapper;
import com.onlineshopping.product.mapper.PmsAttrGroupMapper;
import com.onlineshopping.product.mapper.PmsAttrMapper;
import com.onlineshopping.product.mapper.PmsCategoryMapper;
import com.onlineshopping.product.service.PmsAttrService;
import com.onlineshopping.product.vo.*;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PmsAttrServiceImpl implements PmsAttrService {

    @Resource
    private PmsAttrMapper pmsAttrMapper;

    @Resource
    private PmsAttrAttrgroupRelationMapper attrAttrgroupRelationMapper;

    @Resource
    private PmsAttrGroupMapper pmsAttrGroupMapper;

    @Resource
    private PmsCategoryMapper pmsCategoryMapper;


    @Resource
    private PmsAttrGroupServiceImpl attrGroupService;

    @Override
    public int deleteByPrimaryKey(Long attrId) {
        return pmsAttrMapper.deleteByPrimaryKey(attrId);
    }

    @Override
    public int insert(PmsAttr record) {
        return pmsAttrMapper.insert(record);
    }

    @Override
    public int insertSelective(PmsAttr record) {
        return pmsAttrMapper.insertSelective(record);
    }

    @Override
    public PmsAttr selectByPrimaryKey(Long attrId) {
        return pmsAttrMapper.selectByPrimaryKey(attrId);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsAttr record) {
        return pmsAttrMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PmsAttr record) {
        return pmsAttrMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity selectAll(int page, int limit, String key, Long catelogId, String attrType) {

        //如果attrType==null，查询基本属性；如果attrType==0，只查询销售属性
        attrType= String.valueOf(attrType.equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getMsg())?1:0);
        List<PmsAttr> attrs;
        if(catelogId==0){
            attrs = pmsAttrMapper.selectByKey(key,attrType);
        }else{
            attrs=pmsAttrMapper.selectByKeyAndCatelogId(catelogId,key, attrType);
        }

        List<AttrRespVo> respVos = attrs.stream().map((attrEntiy) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntiy, attrRespVo);
            //根据catelog_id查询所属分类名称
            PmsCategory category = pmsCategoryMapper.selectByPrimaryKey(attrEntiy.getCatelogId());
            //设置所属分类名称
            attrRespVo.setCatelogName(category.getName());
            //根据attrId查询属性是否关联
            PmsAttrAttrgroupRelation attrgroupRelation = attrAttrgroupRelationMapper.selectByAttrId(attrEntiy.getAttrId());

            if(null!=attrgroupRelation){
                //根据att_group_id查询分组名称
                String attrGroupName = pmsAttrGroupMapper.selectByPrimaryKey(attrgroupRelation.getAttrGroupId()).getAttrGroupName();
                attrRespVo.setGroupName(attrGroupName);
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        PageHelper.startPage(page,limit);
        PageInfo<AttrRespVo> pmsAttrPageInfo = new PageInfo<>(respVos);
        PageEntity pageEntity=new PageEntity(pmsAttrPageInfo.getTotal(),pmsAttrPageInfo.getPages(),pmsAttrPageInfo.getList());
        return pageEntity;
    }

    @Override
    public AttrInfoVo selectAttrInfo(Long attrId) {
        PmsAttr pmsAttr = pmsAttrMapper.selectByPrimaryKey(attrId);
        //根据catelog_id查询完整分类路径
        Long[] path = attrGroupService.catelogPath(pmsAttr.getCatelogId());

        PmsAttrAttrgroupRelation attrgroupRelation = attrAttrgroupRelationMapper.selectByAttrId(attrId);
        AttrInfoVo attrUPdateVo=new AttrInfoVo();
        BeanUtils.copyProperties(pmsAttr,attrUPdateVo);
        attrUPdateVo.setCatelogPath(path);
        //如果是销售信息，不设置分组信息
        if(pmsAttr.getAttrType()== ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            if(null!=attrgroupRelation){
                String attrGroupName = pmsAttrGroupMapper.selectByPrimaryKey(attrgroupRelation.getAttrGroupId()).getAttrGroupName();
                attrUPdateVo.setGroupName(attrGroupName);
                attrUPdateVo.setAttrGroupId(attrgroupRelation.getAttrGroupId());
            }
        }

        return attrUPdateVo;
    }

    @Override
    public List<AttrInfoVo> selectBatchByAttrIds(List<Long> attrIds) {
        List<PmsAttr> attrs = pmsAttrMapper.selectBatchByAttrIds(attrIds);
        List<AttrInfoVo> attrInfoVoList=new LinkedList<>();
        for (PmsAttr attr:attrs) {
            AttrInfoVo attrInfoVo=new AttrInfoVo();
            attrInfoVo.setAttrName(attr.getAttrName());
            attrInfoVo.setValueSelect(attr.getValueSelect());
            attrInfoVo.setAttrId(attr.getAttrId());
            attrInfoVoList.add(attrInfoVo);
        }
        return attrInfoVoList;
    }

    /**
     * 获取当前分组没有关联的所有属性
     * @param attrgroupId
     * @param page
     * @param limit
     * @param key
     * @return
     */
    @Override
    public PageEntity getRelation(long attrgroupId, int page, int limit, String key) {
        //根据attrgroupId获取所属分类id
        Long catelogId = pmsAttrGroupMapper.selectByPrimaryKey(attrgroupId).getCatelogId();
        //根据分类id获取当前分类下的分组信息
        List<PmsAttrGroup> attrGroups=pmsAttrGroupMapper.selectByCatelogId(catelogId);
        List<Long> attrGroupIdList=attrGroups.stream().map(item->{
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        //根据分组id查询对应属性关联id
        List<PmsAttrAttrgroupRelation> attrgroupRelations=attrAttrgroupRelationMapper.selectByIdList(attrGroupIdList);
        List<Long> attrIdList=attrgroupRelations.stream().map(item->{
            return item.getAttrId();
        }).collect(Collectors.toList());
        //查询当前(catelog_log)所属分类id下属性id不在attrList集合中的所有属性，即显示的关联属性
        int attrType= ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode();
        List<PmsAttr> attrList=pmsAttrMapper.selectRelation(catelogId,attrIdList,key,attrType);
        List<AttrNoRelationVo> noRelationVoList = new ArrayList<>();
        for (PmsAttr attr: attrList) {
            AttrNoRelationVo attrNoRelationVo=new AttrNoRelationVo();
            BeanUtils.copyProperties(attr,attrNoRelationVo);
            noRelationVoList.add(attrNoRelationVo);
        }
        PageHelper.startPage(page,limit);
        PageInfo<AttrNoRelationVo> pmsAttrPageInfo = new PageInfo<>(noRelationVoList);
        PageEntity pageEntity=new PageEntity(pmsAttrPageInfo.getTotal(),pmsAttrPageInfo.getPages(),pmsAttrPageInfo.getList());
        return pageEntity;
    }

    @Override
    public void batchDeleteByAttrId(List<Long> attrIds) {
        pmsAttrMapper.batchDeleteByAttrId(attrIds);
    }

    @Override
    public Set<Long> selectBySearchType(Long type) {
        return pmsAttrMapper.selectBySearchType(type);
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long catalogId, Long spuId) {
        return pmsAttrMapper.getAttrGroupWithAttrsBySpuId(catalogId,spuId);
    }


}

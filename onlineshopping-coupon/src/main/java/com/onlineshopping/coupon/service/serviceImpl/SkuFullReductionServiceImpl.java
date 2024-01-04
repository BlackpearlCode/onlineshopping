package com.onlineshopping.coupon.service.serviceImpl;

import com.onlineshopping.coupon.entity.SkuFullReduction;
import com.onlineshopping.coupon.entity.SkuLadder;
import com.onlineshopping.coupon.mapper.MemberPriceMapper;
import com.onlineshopping.coupon.mapper.SkuFullReductionMapper;
import com.onlineshopping.coupon.mapper.SkuLadderMapper;
import com.onlineshopping.coupon.service.SkuFullReductionService;
import com.onlineshopping.common.to.MemberPriceTo;
import com.onlineshopping.common.to.SkuReductionTo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkuFullReductionServiceImpl implements SkuFullReductionService{

    @Resource
    private SkuFullReductionMapper skuFullReductionMapper;
    @Resource
    private SkuLadderMapper skuLadderMapper;

    @Resource
    private MemberPriceMapper memberPriceMapper;



    @Override
    public int deleteByPrimaryKey(Long id) {
        return skuFullReductionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SkuFullReduction record) {
        return skuFullReductionMapper.insert(record);
    }

    @Override
    public int insertSelective(SkuFullReduction record) {
        return skuFullReductionMapper.insertSelective(record);
    }

    @Override
    public SkuFullReduction selectByPrimaryKey(Long id) {
        return skuFullReductionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SkuFullReduction record) {
        return skuFullReductionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SkuFullReduction record) {
        return skuFullReductionMapper.updateByPrimaryKey(record);
    }

    @Override
    public void save(SkuReductionTo skuReductionTo) {
        //保存阶梯价格信息
        SkuLadder skuLadder = new SkuLadder();
        skuLadder.setSkuId(skuReductionTo.getSkuId());
        skuLadder.setFullCount(skuReductionTo.getFullCount());
        skuLadder.setDiscount(skuReductionTo.getDiscount());
        //打折状态是否参与其他优惠：0：不参加，1：参加
        boolean addOther = skuReductionTo.getCountStatus() == 1 ? true : false;
        skuLadder.setAddOther(addOther);
        if(skuReductionTo.getFullCount()>0){
            skuLadderMapper.insert(skuLadder);
        }


        //保存满减信息
        SkuFullReduction fullReduction = new SkuFullReduction();
        BeanUtils.copyProperties(skuReductionTo,fullReduction);
        if(fullReduction.getFullPrice().compareTo(new BigDecimal(0))==1){
            skuFullReductionMapper.insert(fullReduction);
        }


        //保存会员价格
        List<MemberPriceTo> memberPrice = skuReductionTo.getMemberPrice();
        List<com.onlineshopping.coupon.entity.MemberPrice> memberPriceList = memberPrice.stream().map(item -> {
            com.onlineshopping.coupon.entity.MemberPrice price = new com.onlineshopping.coupon.entity.MemberPrice();
            price.setSkuId(skuReductionTo.getSkuId());
            price.setMemberPrice(item.getPrice());
            price.setMemberLevelId(item.getId());
            price.setMemberLevelName(item.getName());
            price.setAddOther(true);
            return price;
            //过滤会员价格为0的数据
        }).filter(item->{
            return item.getMemberPrice().compareTo(new BigDecimal(0))==1;
        }).collect(Collectors.toList());
        if(memberPriceList.size()>0){
            memberPriceMapper.batchSave(memberPriceList);
        }

    }

}

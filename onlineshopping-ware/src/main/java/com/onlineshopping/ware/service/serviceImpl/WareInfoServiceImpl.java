package com.onlineshopping.ware.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import com.onlineshopping.ware.entity.WareInfo;
import com.onlineshopping.ware.feign.MemberFeignService;
import com.onlineshopping.ware.mapper.WareInfoMapper;
import com.onlineshopping.ware.service.WareInfoService;
import com.onlineshopping.ware.vo.FareVo;
import com.onlineshopping.ware.vo.MemberAddressVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class WareInfoServiceImpl implements WareInfoService{

    @Resource
    private WareInfoMapper wareInfoMapper;
    @Autowired
    private MemberFeignService memberFeignService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return wareInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WareInfo record) {
        return wareInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(WareInfo record) {
        return wareInfoMapper.insertSelective(record);
    }

    @Override
    public WareInfo selectByPrimaryKey(Long id) {
        return wareInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WareInfo record) {
        return wareInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WareInfo record) {
        return wareInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageEntity getAll(Map<String, Object> params) {

        List<WareInfo> wareInfoList=wareInfoMapper.getAll(params);
        PageHelper.startPage(Integer.parseInt((String) params.get("page")),Integer.parseInt((String) params.get("limit")));
        PageInfo<WareInfo> pageInfo=new PageInfo<>(wareInfoList);
        PageEntity pageEntity=new PageEntity(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
        return pageEntity;
    }

    @Override
    public void batchDeleteById(List<Long> ids) {
        wareInfoMapper.batchDeleteById(ids);
    }

    @Override
    public FareVo getFare(Long id) {
        Result info = memberFeignService.info(id);
        Object obj = info.get("memberReceiveAddress");
        if(null==obj){
            return null;
        }
        Gson gson=new Gson();
        MemberAddressVo memberAddressVo = gson.fromJson(gson.toJson(obj), MemberAddressVo.class);
        String phone = memberAddressVo.getPhone();
        String substring = phone.substring(phone.length() - 1);
        BigDecimal bigDecimal = new BigDecimal(substring);
        FareVo fareVo = new FareVo();
        fareVo.setAddress(memberAddressVo);
        fareVo.setFare(bigDecimal);
        return fareVo ;
    }

}

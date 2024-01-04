package com.onlineshopping.member.controller;

import com.onlineshopping.member.entity.MemberLevel;
import com.onlineshopping.member.service.serviceImpl.MemberLevelServiceImpl;
import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.PageEntity;
import com.onlineshopping.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("member/memberlevel")
@RestController
public class MemberLevelController {

    @Autowired
    private MemberLevelServiceImpl memberLevelService;


    /**
     * 查询会员等级列表
     * @param key：等级名称或等级id
     * @param page: 当前页数
     * @param limit：每页条数
     * @return
     */
    @RequestMapping("/list")
    public Result list(String key,int page,int limit){
        PageEntity pageEntity=memberLevelService.selectAll(key,page,limit);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("page",pageEntity);
    }

    /**
     * 新增会员等级
     * @param memberLevel
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestBody MemberLevel memberLevel){
        memberLevelService.insert(memberLevel);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    /**
     * 根据会员等级id查询会员等级信息
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable Long id){
        MemberLevel memberLevel = memberLevelService.selectByPrimaryKey(id);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg()).put("memberLevel",memberLevel);
    }


    /**
     * 更加会员等级id修改会员等级信息
     * @param memberLevel
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody MemberLevel memberLevel){
        memberLevelService.updateByPrimaryKeySelective(memberLevel);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody List<Long> ids){
        memberLevelService.batchById(ids);
        return Result.r(BizCodeEnum.OK.getCode(), BizCodeEnum.OK.getMsg());
    }
}

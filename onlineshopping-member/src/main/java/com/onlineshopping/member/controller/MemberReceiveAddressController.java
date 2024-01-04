package com.onlineshopping.member.controller;

import com.onlineshopping.member.entity.MemberReceiveAddress;
import com.onlineshopping.member.service.MemberReceiveAddressService;
import com.onlineshopping.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member/memberreceiveaddress")
public class MemberReceiveAddressController {

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @GetMapping("/{memberId}/getAddress")
    public List<MemberReceiveAddress> getAddress(@PathVariable("memberId") Long memberId){
        return memberReceiveAddressService.getAddress(memberId);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id){
        MemberReceiveAddress memberReceiveAddress = memberReceiveAddressService.selectByPrimaryKey(id);
        return Result.ok().put("memberReceiveAddress",memberReceiveAddress);
    }
}

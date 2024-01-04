package com.onlineshopping.order.feign;

import com.onlineshopping.order.vo.MemberAddressVo;
import com.onlineshopping.order.vo.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "onlineshopping-member")
public interface MemberFeignService {

    @GetMapping("/member/memberreceiveaddress/{memberId}/getAddress")
    public List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);

    @RequestMapping("/member/member/findMemberId")
    public MemberVo findMemberId(@RequestParam("memberId") String memberId);
}

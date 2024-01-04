package com.onlineshopping.member.service.serviceImpl;

import com.onlineshopping.member.entity.Member;
import com.onlineshopping.member.entity.MemberLevel;
import com.onlineshopping.member.exception.PhoneExistException;
import com.onlineshopping.member.exception.UsernameExistException;
import com.onlineshopping.member.mapper.MemberMapper;
import com.onlineshopping.member.service.MemberLevelService;
import com.onlineshopping.member.service.MemberService;
import com.onlineshopping.member.vo.MemberLoginVo;
import com.onlineshopping.member.vo.MemberRegistVo;
import com.onlineshopping.member.vo.Oauth2UserInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.text.ParseException;
import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService{

    @Resource
    private MemberMapper memberMapper;
    @Autowired
    private MemberLevelService memberLevelService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return memberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Member record) {
        return memberMapper.insert(record);
    }

    @Override
    public int insertSelective(Member record) {
        return memberMapper.insertSelective(record);
    }

    @Override
    public Member selectByPrimaryKey(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Member record) {
        return memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Member record) {
        return memberMapper.updateByPrimaryKey(record);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        Member member = new Member();
        //设置默认等级
        MemberLevel memberLevel=memberLevelService.getDefaultLevel();
        member.setLevelId(memberLevel.getId());
        //检查用户名和手机号是否唯一。为了让上层cotroller能感知到异常
        checkPhoneUnique(vo.getPhone());
        checkUsernameUnique(vo.getUserName());

        //设置手机号
        member.setMobile(vo.getPhone());
        //设置用户名
        member.setUsername(vo.getUserName());
        member.setNickname(vo.getUserName());
        //设置密码;密码加密存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        member.setPassword(encode);
        //保存
        memberMapper.insertSelective(member);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException{
        int num = memberMapper.selectPhoneISExist(phone);
        if(num>0){
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameExistException{
        int num = memberMapper.seleatUsernameISExist(username);
        if(num>0){
            throw new UsernameExistException();
        }

    }

    @Override
    public Member login(MemberLoginVo vo) {
        Member memberInfo=memberMapper.selectUsernameOrPhone(vo);
        if(memberInfo==null){
            return null;
        }
        //获取数据库的password字段
        String passwordDB = memberInfo.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //2.密码匹配
        boolean matches = passwordEncoder.matches(vo.getPassword(), passwordDB);
        if(!matches){
            return null;
        }
        return memberInfo;
    }

    @Override
    public Member oauth2Login(Oauth2UserInfo userInfo) throws ParseException {
        //获取社交用户唯一标识
        String socialUid = userInfo.getOpenId();
        Member updateMember=memberMapper.selectBySocialUid(socialUid);
        //根据社交账号唯一标识判断该用户是否存在
        if(updateMember!=null){
            return updateMember;
        }
        //如果不存在，则需要注册
        Member member = new Member();
        if(userInfo.getExpiresIn() != 0){
            member.setExpiresIn(userInfo.getExpiresIn());
        }
        if(!StringUtils.isEmpty(userInfo.getAccessToken())){
            member.setAccessToken(userInfo.getAccessToken());
        }
        if(!StringUtils.isEmpty(userInfo.getNickname())){
            member.setNickname(userInfo.getNickname());
        }
        if(!StringUtils.isEmpty(userInfo.getOpenId())){
            member.setSocialUid(userInfo.getOpenId());
        }
        member.setCreateTime(new Date());
        memberMapper.insertSelective(member);
        return member;
    }

    @Override
    public Member selectByMemberId(String memberId) {

        return memberMapper.selectByMemberId(memberId);
    }



}

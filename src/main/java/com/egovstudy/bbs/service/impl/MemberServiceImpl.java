package com.egovstudy.bbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.egovstudy.bbs.mapper.MemberMapper;
import com.egovstudy.bbs.service.MemberService;
import com.egovstudy.bbs.vo.Member;


@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Resource(name="memberMapper")
	MemberMapper memberMapper;
	
	@Override
	public void register(Member member) {
		memberMapper.insert(member);
	}
	
	@Override
	public Member getMember(Member member) {
		return memberMapper.selectByIdAndPw(member);
	}

	@Override
	public void modify(Member member) {
		memberMapper.update(member);
	}

	@Override
	public void secession(String id) {
		memberMapper.delete(id);
	}
	
}

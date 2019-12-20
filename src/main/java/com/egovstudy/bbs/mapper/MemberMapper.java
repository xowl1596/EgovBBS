package com.egovstudy.bbs.mapper;

import com.egovstudy.bbs.vo.Member;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("memberMapper")
public interface MemberMapper {
	public int insert(Member member);
	public Member selectByIdAndPw(Member member);
	public void update(Member member);
	public void delete(String id);
}

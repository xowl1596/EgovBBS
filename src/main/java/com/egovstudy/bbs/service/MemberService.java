package com.egovstudy.bbs.service;

import com.egovstudy.bbs.vo.Member;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface MemberService {
	public void register(Member member);
	public Member getMember(Member member);
	public void modify(Member member);
	public void secession(String id);
}

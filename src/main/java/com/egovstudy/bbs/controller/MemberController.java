package com.egovstudy.bbs.controller;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.egovstudy.bbs.service.MemberService;
import com.egovstudy.bbs.vo.Member;

@Controller
public class MemberController {
	Logger logger = LogManager.getLogger();
	
	@Resource(name="memberService")
	MemberService memberService;
	
	@RequestMapping("/bbs/login.do")
	public String login(){
		return "bbs/login";
	}
	
	@RequestMapping(value="/bbs/loginExec.do", method=RequestMethod.POST)
	public String loginExec(HttpServletRequest req, String id, String pw){
		String returnUrl = "redirect:/bbs/login.do";
		
		Member member = new Member();
		member.setId(id);
		member.setPw(pw);
		
		member = memberService.getMember(member);
		
		HttpSession session = req.getSession();
		
		if(member != null){
			session.setAttribute("member", member);
			returnUrl = "redirect:/bbs/list.do";
		} else{
			session.setAttribute("loginResult", "fail");
		}
		
		return returnUrl;
	}
	
	@RequestMapping("/bbs/mypage.do")
	public String myPage(){
		return "/bbs/myPage";
	}
	
	@RequestMapping("/bbs/membermodify.do")
	public String memberModify(){
		return "bbs/memberModify";
	}
	
	@RequestMapping(value="/bbs/memberModifyExec.do", method=RequestMethod.POST)
	public String memberModifyExec(HttpServletRequest req, Member member){
		HttpSession session = req.getSession();
		Member origin = (Member) session.getAttribute("member");
		member.setId(origin.getId());
		if(member.getPw() == ""){
			member.setPw(origin.getPw());
		}
		memberService.modify(member);
		session.setAttribute("member", member);
		return "redirect:/bbs/mypage.do";
	}
	
	@RequestMapping("/bbs/logout.do")
	public String logout(HttpServletRequest req){
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/bbs/list.do";
	}
	
	@RequestMapping("/bbs/secession.do")
	public String secession(HttpServletRequest req){
		HttpSession session = req.getSession();
		String id = ((Member) session.getAttribute("member")).getId();
		
		memberService.secession(id);
		
		session.invalidate();
		
		return "redirect:/bbs/list.do";
	}
	
	@RequestMapping("/bbs/register.do")
	public String register(Member member){
		return "bbs/register";
	}
	
	@RequestMapping("/bbs/registerExec.do")
	public String registerExec(Member member){
		memberService.register(member);
		return "redirect:/bbs/list.do";
	}
}

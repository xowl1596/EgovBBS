package com.egovstudy.bbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.egovstudy.bbs.mapper.PostMapper;
import com.egovstudy.bbs.service.PostService;
import com.egovstudy.bbs.vo.Member;
import com.egovstudy.bbs.vo.Post;

@Controller
public class PostController {
	Logger logger = LogManager.getLogger();
	
	@Resource(name="postService")
	PostService postService;
	
	@RequestMapping("/bbs/list.do")
	public String list(){
		return "bbs/list";
	}
	
	@RequestMapping("/bbs/write.do")
	public String write(){
		return "bbs/write";
	}
	
	@RequestMapping(value="/bbs/post/{id}")
	public ModelAndView post(@PathVariable int id){
		Post post = postService.getById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("post", post);
		mav.setViewName("/bbs/post");
		return mav;
	}
	
	@RequestMapping(value="/bbs/posts", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Post>> posts(){
		List<Post> ls = postService.getAll();
		Map<String , List<Post>> list = new HashMap<String, List<Post>>();
		list.put("data", ls);
		return list;
	}
	
	@RequestMapping(value="/bbs/writeExec.do", method=RequestMethod.POST)
	public String writeExec(HttpServletRequest req, Post post){
		HttpSession session = req.getSession();
		
		String writer = ((Member) session.getAttribute("member")).getId();
		post.setWriter(writer);
		
		postService.write(post);
		logger.debug(post.getId());
		
		return "redirect:/bbs/post/" + post.getId() + ".do";
	}

	@RequestMapping("/bbs/postModify/{id}.do")
	public ModelAndView modify(HttpServletRequest req, @PathVariable int id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/bbs/postModify");
		
		HttpSession session = req.getSession();
		
		Member member = (Member) session.getAttribute("member");
		Post post = postService.getById(id);
		
		if(member != null && member.getId().equals(post.getWriter())){
			mav.addObject("post", post);
		} else {
			session.setAttribute("idCheckResult", "fail");
			mav.setViewName("redirect:/bbs/post/"+post.getId()+".do");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/bbs/postModifyExec.do", method = RequestMethod.POST)
	public String modifyExec(Post post){
		postService.modify(post);
		return "redirect:/bbs/post/" + post.getId() + ".do";
	}
	
	@RequestMapping(value="/bbs/postDelete/{id}.do")
	public ModelAndView postDelete(HttpServletRequest req, @PathVariable int id){
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = req.getSession();
		
		Member member = (Member) session.getAttribute("member");
		Post post = postService.getById(id);
		
		if(member != null && member.getId().equals(post.getWriter())){
			postService.remove(id);
			mav.setViewName("redirect:/bbs/list.do");
		} else {
			mav.setViewName("redirect:/bbs/post/" + id + ".do");
		}
		
		return mav;
	}
}

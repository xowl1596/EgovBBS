package com.egovstudy.bbs.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.egovstudy.bbs.service.impl.CommentServiceImpl;
import com.egovstudy.bbs.vo.Comment;
import com.egovstudy.bbs.vo.Member;

@Controller
public class CommentController {
	@Resource(name="commentService")
	CommentServiceImpl commentService;
	
	@RequestMapping(value="/bbs/comment/write/{id}.do", method=RequestMethod.POST)
	public String commentWrite(HttpServletRequest req,Comment comment, @PathVariable int id){
		String resultUrl = "";
		
		HttpSession session = req.getSession();
		
		Member member = (Member) session.getAttribute("member");
		
		if(member != null){
			comment.setWriter(member.getId());
			comment.setPost(id);
			
			commentService.write(comment);	
			resultUrl = "redirect:/bbs/post/" + id +".do";
		} else{
			resultUrl = "redirect:/bbs/post/" + id + ".do";
		}
		
		return resultUrl;
	}
	
	@RequestMapping(value="/bbs/comment/list/{postId}", method=RequestMethod.GET)
	@ResponseBody
	public List<Comment> getCommentList(@PathVariable int postId){
		List<Comment> list = commentService.readByPostId(postId);
		
		return list;
	}
}

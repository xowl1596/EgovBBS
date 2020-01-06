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
	
	@RequestMapping(value="/bbs/comment/write/{postId}.do", method=RequestMethod.POST)
	public String commentWrite(HttpServletRequest req,Comment comment, @PathVariable int postId){
		String resultUrl = "";
		
		HttpSession session = req.getSession();
		
		Member member = (Member) session.getAttribute("member");
		
		if(member != null){
			comment.setWriter(member.getId());
			comment.setPost(postId);
			
			commentService.write(comment);	
			resultUrl = "redirect:/bbs/post/" + postId +".do";
		} else{
			resultUrl = "redirect:/bbs/post/" + postId + ".do";
		}
		
		return resultUrl;
	}
	
	@RequestMapping(value="/bbs/comment/list/{postId}", method=RequestMethod.GET)
	@ResponseBody
	public List<Comment> getCommentList(@PathVariable int postId){
		List<Comment> list = commentService.readByPostId(postId);
		
		return list;
	}
	
	@RequestMapping(value="/bbs/comment/delete/{postId}/{id}", method=RequestMethod.GET)
	public String deleteComment(@PathVariable int postId ,@PathVariable int id){
		commentService.remove(id);
		return "redirect:/bbs/post/" + postId + ".do";
	}
	
	@RequestMapping(value="/bbs/comment/modify/{postId}/{id}.do")
	public String modifyComment(@PathVariable int postId,@PathVariable int id, String content){
		Comment comment = new Comment();
		comment.setId(id);
		comment.setContent(content);
		
		commentService.modify(comment);
		return "redirect:/bbs/post/" + postId + ".do";
	}
}

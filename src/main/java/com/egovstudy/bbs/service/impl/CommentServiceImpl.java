package com.egovstudy.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.egovstudy.bbs.mapper.CommentMapper;
import com.egovstudy.bbs.service.CommentService;
import com.egovstudy.bbs.vo.Comment;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
	@Resource(name="commentMapper")
	CommentMapper commentMapper;
	
	@Override
	public void write(Comment comment){
		commentMapper.insert(comment);
	}

	@Override
	public List<Comment> readByPostId(int id) {
		return commentMapper.getListByPostId(id);
	}

	@Override
	public void remove(int id) {
		commentMapper.delete(id);
	}

	@Override
	public void modify(Comment comment) {
		commentMapper.update(comment);
	}
}

package com.egovstudy.bbs.service;

import java.util.List;

import com.egovstudy.bbs.vo.Comment;

public interface CommentService {
	public void write(Comment comment);
	public List<Comment> readByPostId(int id);
}

package com.egovstudy.bbs.mapper;

import java.util.List;

import com.egovstudy.bbs.vo.Comment;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("commentMapper")
public interface CommentMapper {
//	CREATE
	void insert(Comment comment);
//	READ
	List<Comment> getListByPostId(int id);
//	UPDATE
	void update(Comment comment);
//	DELETE
	void delete(int id);
}

package com.egovstudy.bbs.mapper;

import java.util.List;

import com.egovstudy.bbs.vo.Post;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("postMapper")
public interface PostMapper {
	public void insert(Post post);
	public List<Post> selectAll();
	public Post selectById(int id);
	public void update(Post post);
	public void delete(int id);
}

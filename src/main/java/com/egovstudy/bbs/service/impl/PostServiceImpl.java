package com.egovstudy.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.egovstudy.bbs.mapper.PostMapper;
import com.egovstudy.bbs.service.PostService;
import com.egovstudy.bbs.vo.Post;

@Service("postService")
public class PostServiceImpl implements PostService {
	@Resource(name="postMapper")
	PostMapper postMapper;

	@Override
	public void write(Post post){
		postMapper.insert(post);
	}
		
	@Override
	public List<Post> getAll() {
		return postMapper.selectAll();
	}

	@Override
	public Post getById(int id) {
		return postMapper.selectById(id);
	}
	
	@Override
	public void modify(Post post){
		postMapper.update(post);		
	}
	
	@Override
	public void remove(int id){
		postMapper.delete(id);
	}
}

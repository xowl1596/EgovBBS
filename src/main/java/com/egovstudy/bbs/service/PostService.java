package com.egovstudy.bbs.service;

import java.util.List;

import com.egovstudy.bbs.vo.Post;

public interface PostService {
	public void write(Post post);
	public List<Post> getAll();
	public Post getById(int id);
	public void modify(Post post);
	public void remove(int id);
}

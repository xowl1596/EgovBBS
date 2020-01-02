package com.egovstudy.bbs.service;

import java.util.List;

import com.egovstudy.bbs.vo.ImagePost;

public interface ImagePostService {
	public void write(ImagePost post);
	public List<ImagePost> readAll();
	public void updateThumbnailPath(ImagePost post);
	public ImagePost readById(int id);
}

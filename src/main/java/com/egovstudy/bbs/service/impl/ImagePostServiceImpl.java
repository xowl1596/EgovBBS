package com.egovstudy.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.egovstudy.bbs.mapper.ImagePostMapper;
import com.egovstudy.bbs.service.ImagePostService;
import com.egovstudy.bbs.vo.ImagePost;

@Service("imagePostService")
public class ImagePostServiceImpl implements ImagePostService {
	@Resource(name="imagePostMapper")
	ImagePostMapper imagePostMapper;

	@Override
	public void write(ImagePost post) {
		imagePostMapper.insert(post);
	}

	@Override
	public List<ImagePost> readAll() {
		return imagePostMapper.selectAll();
	}

	@Override
	public void updateThumbnailPath(ImagePost post) {
		imagePostMapper.updateThumbnailPath(post);
	}

	@Override
	public ImagePost readById(int id) {
		return imagePostMapper.selectById(id);
	}

	@Override
	public void remove(int id) {
		imagePostMapper.delete(id);
	}

	@Override
	public void update(ImagePost post) {
		imagePostMapper.update(post);
	}
}

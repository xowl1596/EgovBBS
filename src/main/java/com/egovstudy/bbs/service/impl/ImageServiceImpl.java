package com.egovstudy.bbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.egovstudy.bbs.mapper.ImageMapper;
import com.egovstudy.bbs.service.ImageService;
import com.egovstudy.bbs.vo.Image;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	@Resource(name="imageMapper")
	private ImageMapper imageMapper;

	@Override
	public void upload(Image image) {
		imageMapper.insert(image);
	}

	@Override
	public Image readByPostAndSeq(Image image) {
		return imageMapper.selectByPostAndSeq(image);
	}

	@Override
	public List<Image> readByPost(int post) {
		return imageMapper.selectByPost(post);
	}

	@Override
	public void removeByPostId(int id) {
		imageMapper.delete(id);
	}
}

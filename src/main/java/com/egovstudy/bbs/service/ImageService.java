package com.egovstudy.bbs.service;

import java.util.List;

import com.egovstudy.bbs.vo.Image;

public interface ImageService {
	public void upload(Image image);
	public Image readByPostAndSeq(Image image);
	public List<Image> readByPost(int post);
}

package com.egovstudy.bbs.mapper;

import java.util.List;

import com.egovstudy.bbs.vo.Image;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("imageMapper")
public interface ImageMapper {
	public void insert(Image image);
	public Image selectByPostAndSeq(Image image);
	public List<Image> selectByPost(int post);
	public void delete(int post);
}

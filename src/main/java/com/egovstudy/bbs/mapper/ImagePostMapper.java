package com.egovstudy.bbs.mapper;

import java.util.List;

import com.egovstudy.bbs.vo.ImagePost;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("imagePostMapper")
public interface ImagePostMapper {
	public void insert(ImagePost post);
	public List<ImagePost> selectAll();
	public void updateThumbnailPath(ImagePost post);
	public ImagePost selectById(int id);
	public void delete(int id);
	public void update(ImagePost post);
}

package com.egovstudy.bbs.vo;

public class Image {
	private String path;
	private int post;
	private int seq;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}

package com.dhkh.model;

import java.sql.Timestamp;

public class Post {
	private int postId;
	private String title;
	private String content;
	private Timestamp postTime;
	private String postTimeStr;
	private int userId;
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}

	public String getPostTimeStr() {
		return postTimeStr;
	}

	public void setPostTimeStr(String postTimeStr) {
		this.postTimeStr = postTimeStr;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
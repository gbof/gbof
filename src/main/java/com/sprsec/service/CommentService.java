package com.sprsec.service;

import java.util.List;

import com.sprsec.model.Comment;

public interface CommentService {
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId);
	public List<Comment> getAllComments();
	public List<Comment> getConfirmedComments();
}
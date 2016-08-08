package com.sprsec.dao;

import java.util.List;

import com.sprsec.model.Comment;

public interface CommentDAO {
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId);
	public List<Comment> getAllComments();
	public List<Comment> getConfirmedComments();
}

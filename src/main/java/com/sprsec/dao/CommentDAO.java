package com.sprsec.dao;

public interface CommentDAO {
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId);
}

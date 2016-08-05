package com.sprsec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprsec.dao.CommentDAO;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDAO commentDAO;
	
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id){
		commentDAO.addComment(message1, message2, ballsNumber, user_id);
	}
}

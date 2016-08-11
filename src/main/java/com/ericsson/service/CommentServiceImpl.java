package com.ericsson.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.dao.CommentDAO;
import com.ericsson.model.Comment;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDAO commentDAO;
	
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId){
		commentDAO.addComment(message1, message2, ballsNumber, user_id, commentToUserId);

	}
	public List<Comment> getAllComments() {
		return commentDAO.getAllComments();
	}
	public List<Comment> getConfirmedComments() {
		return commentDAO.getConfirmedComments();

	}
	
	public List<Double> getBallValue(Double money){
		return commentDAO.getBallValue(money);
	}
	public List<Long> getBallValue2(){
		return commentDAO.getBallValue2();
	}
	public List<Integer> getAllBallsGivenTo(Integer id, Integer id2){
		return commentDAO.getAllBallsGivenTo(id, id2);
	}
	public List<Comment> getYourComments(Integer id)
	{
		return commentDAO.getYourComments(id);
		}
}

package com.ericsson.service;

import java.util.List;

import com.ericsson.model.Comment;

public interface CommentService {
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId);
	public void editComment(String message1, String message2, Integer ballsNumber, Integer com_id);
	public List<Comment> getAllComments();
	public List<Comment> getConfirmedComments();
	public List<Double> getBallValue(Double money);
	public List<Long> getBallValue2();
	public List<Integer> getAllBallsGivenTo(Integer id, Integer id2);
	public List<Comment> getYourComments(Integer id);


	public List<Comment> getCommentsYouGave(Integer integer);
	public void removeComment(Integer comId);

	public boolean checkNull(String string);
	public void setConfirm(Integer id);
 

	public Comment getCommentId(Integer id);
	public void setConfirmAll();
	public List<Double> getCash();
	public void editCommentBalls(Integer com_id);
	public void archiveComments();

}

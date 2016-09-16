package com.ericsson.dao;

import java.util.List;

import com.ericsson.model.Ball;

public interface BallDAO {
	
	public void addBall(Integer received_balls, Integer balls_to_give, boolean locked, double cash);
	public List<Ball> getBallId();
	public void editBallsToGive(Integer balls_id, Integer balls);
	public void removeBalls(Integer balls_id);
	public List<Integer> getBallsToGive();
	public List<Integer> getReceivedMoney(Integer balls_id, Double money);
	public void editCash(Integer balls_id, Double cash);
	public void editLocked(Integer balls_id, Integer locked);
	public void editBallsToGiveAndRecivedBallsAfterCommentArchive(Integer balls_id, Integer balls);
}

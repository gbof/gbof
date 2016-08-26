package com.ericsson.dao;

import java.util.List;

import com.ericsson.model.Ball;

public interface BallDAO {
	
	public void addBall(Integer received_balls, Integer balls_to_give, boolean locked, double cash);
	public List<Ball> getBallId();
	public void editBallsToGive(Integer user_id, Integer balls);
	public void removeBalls(Integer balls_id);

}

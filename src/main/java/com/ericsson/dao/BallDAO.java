package com.ericsson.dao;

import java.util.List;

import com.ericsson.model.Ball;

public interface BallDAO {
	
	public void addBall(Integer received_balls, Integer balls_to_give, boolean locked, double cash);
	public List<Ball> getBallId();

}

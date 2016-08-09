package com.sprsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprsec.dao.BallDAO;
import com.sprsec.model.Ball;

@Service
@Transactional
public class BallServiceImpl implements BallService {

		@Autowired
		private BallDAO ballDAO;
		
		public void addBall(Integer received_balls, Integer balls_to_give, boolean locked, double cash){
			ballDAO.addBall(received_balls, balls_to_give, locked, cash);
		}

		@Override
		public List<Ball> getBallId() {
			return ballDAO.getBallId();
		}
}

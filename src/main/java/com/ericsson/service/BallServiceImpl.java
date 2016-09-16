package com.ericsson.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.dao.BallDAO;
import com.ericsson.model.Ball;

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

		@Override
		public void editBallsToGive(Integer balls_id, Integer balls) {
			ballDAO.editBallsToGive(balls_id, balls);
			
		}

		@Override
		public void removeBalls(Integer balls_id) {
			ballDAO.removeBalls(balls_id);
		}
		
		@Override
		public List<Integer> getBallsToGive(){
			return ballDAO.getBallsToGive();
		}
		
		@Override
		public List<Integer> getReceivedMoney(Integer balls_id, Double money){
			return ballDAO.getReceivedMoney(balls_id, money);
		}
		
		@Override
		public void editCach(Integer balls_id, Double cash){
			ballDAO.editCash(balls_id, cash);
		}
		
		@Override
		public void editLocked(Integer balls_id, Integer locked){
			ballDAO.editLocked(balls_id, locked);
		}
		
		@Override
		public void editBallsToGiveAndRecivedBallsAfterCommentArchive(Integer balls_id, Integer balls){
			ballDAO.editBallsToGiveAndRecivedBallsAfterCommentArchive(balls_id, balls);
		}
		
}

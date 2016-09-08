package com.ericsson.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ericsson.model.Ball;
import com.ericsson.model.User;

@Repository
public class BallDAOImpl implements BallDAO {

	@Autowired
	private SessionFactory sessionFactory;

	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
   
	@Override
	public void addBall(Integer received_balls, Integer balls_to_give, boolean locked, double cash) {
		

		
		Integer lockedInt;
		if (locked == false){
			lockedInt=0;
		} else {
			lockedInt=1;
		}
		
		String query = "INSERT INTO balls (received_balls, balls_to_give, locked, cash) VALUES ('"+ received_balls +"', '"+ balls_to_give +"', '"+ lockedInt +"', '"+ cash +"')";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	
	public List<Ball> getBallId() {
		List<Ball> ballsList = new ArrayList<Ball>();
		
		String sql = "from Ball";
		Query query = openSession().createQuery(sql);
		
		ballsList = query.list();
		if (ballsList.size() > 0)
			return ballsList;
		else
			return null;	
	}

	@Override
	public void editBallsToGive(Integer balls_id, Integer balls) {
		String query = "UPDATE balls SET balls_to_give='"+balls+"'  WHERE balls_id ='"+balls_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}
	
	@Override
	public void editCach(Integer balls_id, Double cash) {
		String query = "UPDATE balls SET cash='"+cash+"'  WHERE balls_id ='"+balls_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}
	
	@Override
	public void editLocked(Integer balls_id, Integer locked) {
		String query = "UPDATE balls SET locked='"+locked+"'  WHERE balls_id ='"+balls_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}
	
	
	@Override
	public List<Integer> getBallsToGive(){
		List<Integer> ballsList = new ArrayList<Integer>();
		
		String sql = "select sum(balls_to_give) from Ball";
		Query query = openSession().createQuery(sql);
		
		ballsList = query.list();
		if (ballsList.size() > 0)
			return ballsList;
		else
			return null;
	}

	@Override
	public void removeBalls(Integer balls_id) {
		String query = "delete from balls where balls_id="+balls_id;
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}
	
	@Override
	public List<Integer> getReceivedMoney(Integer balls_id, Double money) {
		List<Integer> ballsList = new ArrayList<Integer>();
		String sql = "select received_balls from Ball where balls_id='"+balls_id+"'";
		Query query = openSession().createQuery(sql);
		ballsList = query.list();
		if (ballsList.size() > 0)
			return ballsList;
		else
			return null;
		
	}
	
	
	
}

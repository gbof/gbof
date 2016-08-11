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

}

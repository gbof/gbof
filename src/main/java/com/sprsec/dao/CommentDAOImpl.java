package com.sprsec.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAOImpl implements CommentDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
    
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId){
		String query = "INSERT INTO comments (user_id, first_com, second_com, confirmed, creator_id, balls_per_com) VALUES ('"+ commentToUserId +"', '"+ message1 +"', '"+ message2 +"', '"+ 0 +"', '"+ user_id +"', '"+ ballsNumber +"')";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}

}

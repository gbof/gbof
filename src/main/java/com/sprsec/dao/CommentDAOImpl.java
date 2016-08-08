package com.sprsec.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sprsec.model.Comment;
import com.sprsec.model.User;

@Repository
public class CommentDAOImpl implements CommentDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	public List<Comment> getAllComments() {
		List<Comment> commentsList = new ArrayList<Comment>();
		
		String sql = "FROM Comment WHERE confirmed = false";
		Query query = openSession().createQuery(sql);
		
		commentsList = query.list();
		if (commentsList.size() > 0)
			return commentsList;
		else
			return null;	
	}
	public List<Comment> getConfirmedComments() {
		List<Comment> commentsConfirmedList = new ArrayList<Comment>();
		Boolean conf = true;
		String sql = "from Comment where confirmed = true";
		Query query = openSession().createQuery(sql);
		
		commentsConfirmedList = query.list();
		if (commentsConfirmedList.size() > 0)
			return commentsConfirmedList;
		else
			return null;	
	}
    
	public void addComment(String message1, String message2, Integer ballsNumber, Integer user_id, Integer commentToUserId){
		String query = "INSERT INTO comments (user_id, first_com, second_com, confirmed, creator_id, balls_per_com) VALUES ('"+ commentToUserId +"', '"+ message1 +"', '"+ message2 +"', '"+ 0 +"', '"+ user_id +"', '"+ ballsNumber +"')";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}

}

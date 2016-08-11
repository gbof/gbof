package com.ericsson.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.ericsson.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	 
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

	public User getUser(String login) {
		List<User> userList = new ArrayList<User>();
		logger.info("Login: " + login);
		Query query = openSession().createQuery("from User u where u.login = :login");
		query.setParameter("login", login);
		userList = query.list();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;	
	}
	
	public User getUserId(Integer id) {
		List<User> userList = new ArrayList<User>();
		Query query = openSession().createQuery("from User u where u.id = :id");
		query.setParameter("id", id);
		userList = query.list();
		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;	
	}
	

	public List<User> getAllUsers() {
		List<User> usersList = new ArrayList<User>();
		
		String sql = "from User";
		Query query = openSession().createQuery(sql);
		
		usersList = query.list();
		if (usersList.size() > 0)
			return usersList;
		else
			return null;	
	}
	
	
	public void setPassword(Integer id, String password){
		String query = "UPDATE users SET password = '"+ password +"' WHERE user_id = '"+ id + "'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
	
	public void setBallsAfterComment(Integer id, Integer balls, Integer commentToUserId){
		Integer ballsToGive = getUserId(id).getBall().getBallsToGive()-balls;
		Integer reveivedBalls = getUserId(commentToUserId).getBall().getReceivedBalls()+balls;
		String query = "UPDATE balls b, users u set b.balls_to_give = '" + ballsToGive + "' where u.user_id = '" + id + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		String query1 = "UPDATE balls b, users u set b.received_balls = '" + reveivedBalls + "' where u.user_id = '" + commentToUserId + "' and b.balls_id = u.balls_id";
		SQLQuery sqlQuery1 = openSession().createSQLQuery(query1);
		sqlQuery.executeUpdate();
		sqlQuery1.executeUpdate();
	}

	@Override
	public void addUser(String name, String surname, String login, String password, Integer roleID, Integer teamID, Integer ballsID, String mail, Integer deptID){
			String query = "INSERT INTO users (name, surname, login, password, role_id, team_id, balls_id, mail, dept_id) VALUES ('" + name +"', '"+ surname +"', '"+ login +"', '"+ password +"', '"+ roleID +"', '"+teamID+"', '"+ballsID+"', '"+mail+"', '"+deptID+"')";
			SQLQuery sqlQuery = openSession().createSQLQuery(query);
			sqlQuery.executeUpdate();
		}

	@Override
	public void removeUser(Integer user_id) {
		String query = "delete from users where user_id="+user_id;
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}
		

	
	
}
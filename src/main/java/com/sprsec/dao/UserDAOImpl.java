package com.sprsec.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.sprsec.model.User;

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

}
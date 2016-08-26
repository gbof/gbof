package com.ericsson.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRolesDAOImpl implements UserRolesDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void add(Integer userId, Integer roleId) {
		String query = "INSERT INTO user_roles (user_id, role_id) VALUES ('" + userId +"', '"+ roleId +"')";
		
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}

	/*@Override
	public void editUserRoles(Integer userId, Integer roleId) {
		String query = "UPDATE user_roles SET user_id='"+userId+"', role_id='"+roleId+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}*/

}
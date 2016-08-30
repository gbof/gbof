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
	
	@Override
	public void removeUserRole(Integer user_id) {
		String query = "delete from user_roles where user_id="+user_id;
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}

	@Override
	public void editRole(Integer user_id, Integer roleID) {
		String query = "UPDATE user_roles SET role_id='"+roleID+"' where user_id='"+user_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
		
	}


}

package com.sprsec.dao;

import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsDAOImpl implements SettingsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, Date deadline,Boolean freeze, Integer SettingId){
		//String queryDel = "DELETE * FROM settings";
		//SQLQuery sqlQuery = openSession().createSQLQuery(queryDel);
		String queryInsert = "INSERT INTO settings (extra_balls, balls_per_person, money, deadline, freeze, SettingId) VALUES ('"+ extraBalls +"', '"+ balls_per_pers +"', '"+ money +"', '"+ deadline +"', '"+ freeze +"', '"+ SettingId +"')";
		SQLQuery sqlQuery = openSession().createSQLQuery(queryInsert);
		sqlQuery.executeUpdate();
	}

}

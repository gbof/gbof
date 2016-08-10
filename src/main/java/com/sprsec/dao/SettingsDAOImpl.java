package com.sprsec.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sprsec.model.Settings;

@Repository
public class SettingsDAOImpl implements SettingsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer settings_id){
		//String queryDel = "DELETE * FROM settings";
		//SQLQuery sqlQuery = openSession().createSQLQuery(queryDel);
		Integer freezeId;
		if(freeze==true)
		{
			freezeId=1;
		}
		else
		{
			freezeId=0;
		}
		String queryInsert = "INSERT INTO settings (extra_balls, balls_per_person, money, deadline, freeze,balls_left, settings_id) VALUES ('"+ extraBalls +"', '"+ balls_per_pers +"', '"+ money +"', '"+ deadline +"','"+ 0 +"', '"+ freezeId +"', '"+ settings_id +"')";
		SQLQuery sqlQuery = openSession().createSQLQuery(queryInsert);
		sqlQuery.executeUpdate();
	}
	
	public List<Double> getMoney(Integer settingsId){
		List<Double> getMoney = new ArrayList<Double>();
		String sql = "select money from Settings WHERE settings_id = '"+ settingsId + "'";
		Query query = openSession().createQuery(sql);
		getMoney = query.list();
		if (getMoney.size() > 0)
			return getMoney;
		else
			return null;	
	
	}
	
	@Override
	public List<Settings> getAllSettings() {
		List<Settings> settingsList = new ArrayList<Settings>();
		
		String sql = "from Settings";
		Query query = openSession().createQuery(sql);
		
		settingsList = query.list();
		if (settingsList.size() > 0)
			return settingsList;
		else
			return null;	
	}

}

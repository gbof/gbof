package com.ericsson.dao;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ericsson.model.Settings;
import com.ericsson.model.User;
import com.ericsson.service.BallService;
import com.ericsson.model.Settings;

@Repository
public class SettingsDAOImpl implements SettingsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private BallService ballservice;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void addSetting(Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer settings_id,String helpMsg){
		
			
		Integer freezeId;
		if(freeze==true)
		{
			freezeId=1;
		}
		else
		{
			freezeId=0;
		}

		
		String queryInsert = "UPDATE settings SET balls_per_person='"+balls_per_pers+"', money='"+money+"', deadline='"+deadline+"', freeze='"+freezeId+"',balls_left='"+ballservice.getBallsToGive().get(0)+"',helpMsg='"+helpMsg+"' WHERE settings_id='"+settings_id+"'";
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
	
	public void addExtraMoney(Integer settings_id, Double withExtraMoney)
	{
		
		String queryInsert = "UPDATE settings SET money='"+withExtraMoney+"' WHERE settings_id='"+settings_id+"'";
		SQLQuery sqlQuery = openSession().createSQLQuery(queryInsert);

		sqlQuery.executeUpdate();
	}

	
	@Override
	

	public List<Settings> getSettings() {

		List<Settings> settingsList = new ArrayList<Settings>();
		
		String sql = "from Settings";
		Query query = openSession().createQuery(sql);
		
		settingsList = query.list();
		if (settingsList.size() > 0)
			return settingsList;
		else
			return null;	
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    double money = (double) tmp/factor;
	    return money;
	}
	
	public List<Integer> getSettingsFreeze() {
		List<Integer> integerList = new ArrayList<Integer>();
		
		String sql = "select freeze from Settings";
		Query query = openSession().createQuery(sql);
		
		integerList = query.list();
		if (integerList.size() > 0)
			return integerList;
		else
			return null;
	}
	
	public List<Date> getSettingsDate() {
		List<Date> integerList = new ArrayList<Date>();
		
		String sql = "select deadline from Settings";
		Query query = openSession().createQuery(sql);
		
		integerList = query.list();
		if (integerList.size() > 0)
			return integerList;
		else
			return null;
	}
	
	public void setToFrozen(){	
		String query = "UPDATE settings SET freeze='1'";
		SQLQuery sqlQuery = openSession().createSQLQuery(query);
		sqlQuery.executeUpdate();
	}

}

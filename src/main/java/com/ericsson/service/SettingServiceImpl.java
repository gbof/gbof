package com.ericsson.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.dao.SettingsDAO;
import com.ericsson.model.Settings;

@Service
@Transactional
public class SettingServiceImpl implements SettingService{
	
	@Autowired
	private SettingsDAO settingsDAO;
	
	public void addSetting(Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer SettingId,String helpMsg)
	{
		settingsDAO.addSetting(balls_per_pers, money, deadline, freeze, SettingId, helpMsg);
	}
	
	public List<Double> getMoney(Integer settingsId)
	{
		return settingsDAO.getMoney(settingsId);
	}

	

	public List<Settings> getSettings()
	{
		return settingsDAO.getSettings();
 
	}
	
	public double round(double value, int places){
		return settingsDAO.round(value, places);
	}
	
	public List<Integer> getSettingsFreeze(){
		return settingsDAO.getSettingsFreeze();
	}
	
	public List<Date> getSettingsDate(){
		return settingsDAO.getSettingsDate();
	}
	
	public void setToFrozen(){
		settingsDAO.setToFrozen();
	}
	public void addExtraMoney(Integer settings_id, Double withExtraMoney){
		
		settingsDAO.addExtraMoney(settings_id, withExtraMoney);
	}
}

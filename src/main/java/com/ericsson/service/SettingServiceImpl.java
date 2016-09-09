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

	public void addNewSettings(Integer settings_id)
	{
		settingsDAO.addNewSettings(settings_id);
	}
	public void deleteSettings(Integer settings_id)
	{
		settingsDAO.deleteSettings(settings_id);
	}
	public List<Settings> getSettings(Integer settings_id)
	{
		return settingsDAO.getSettings(settings_id);
 
	}
	
	public double round(double value, int places){
		return settingsDAO.round(value, places);
	}
	
	public List<Integer> getSettingsFreeze(Integer settings_id){
		return settingsDAO.getSettingsFreeze(settings_id);
	}
	
	public List<Date> getSettingsDate(Integer settings_id){
		return settingsDAO.getSettingsDate(settings_id);
	}
	
	public void setToFrozen(Integer settings_id){
		settingsDAO.setToFrozen(settings_id);
	}
	public void addExtraMoney(Integer settings_id, Double withExtraMoney){
		
		settingsDAO.addExtraMoney(settings_id, withExtraMoney);
	}
}

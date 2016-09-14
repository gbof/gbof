package com.ericsson.dao;

import java.util.Date;
import java.util.List;

import com.ericsson.model.Settings;

public interface SettingsDAO {


	public List<Double> getMoney(Integer settingsId);

	public void addSetting(Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer settings_id, String heplMsg);
	
	public void addNewSettings(Integer settings_id);
	
	public void deleteSettings(Integer settings_id);
	
	public List<Settings> getSettings(Integer settings_id);
	
	public double round(double value, int places);
	
	public List<Integer> getSettingsFreeze(Integer settings_id);
	
	public List<Date> getSettingsDate(Integer settings_id);
	
	public void setToFrozen(Integer settings_id);
	
	public void addExtraMoney(Integer settings_id, Double withExtraMoney);

}

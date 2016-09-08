package com.ericsson.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ericsson.model.Settings;

import com.ericsson.model.Settings;

public interface SettingsDAO {


	public List<Double> getMoney(Integer settingsId);

	public void addSetting(Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer settings_id, String heplMsg);
	

	public List<Settings> getSettings(Integer settings_id);
	
	public double round(double value, int places);
	
	public List<Integer> getSettingsFreeze();
	
	public List<Date> getSettingsDate();
	
	public void setToFrozen();
	
	public void addExtraMoney(Integer settings_id, Double withExtraMoney);

}

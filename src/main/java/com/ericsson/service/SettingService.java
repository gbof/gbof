package com.ericsson.service;


import java.util.List;

import com.ericsson.model.Settings;

import com.ericsson.model.Settings;


public interface SettingService {

	
	public List<Double> getMoney(Integer settingsId);

	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer SettingId);



	public List<Settings> getSettings();
	
	public double round(double value, int places);
	
	public List<Integer> getSettingsFreeze();


}

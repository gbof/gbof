package com.sprsec.service;


import java.util.Date;
import java.util.List;

import com.sprsec.model.Settings;


public interface SettingService {

	
	public List<Double> getMoney(Integer settingsId);

	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer SettingId);

	public List<Settings> getAllSettings();
}

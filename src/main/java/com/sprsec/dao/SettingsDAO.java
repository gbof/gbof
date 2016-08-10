package com.sprsec.dao;

import java.util.List;

import com.sprsec.model.Settings;

import com.sprsec.model.Settings;

public interface SettingsDAO {


	public List<Double> getMoney(Integer settingsId);

	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer settings_id);
	



	public List<Settings> getSettings();

}

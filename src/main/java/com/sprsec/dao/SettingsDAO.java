package com.sprsec.dao;

import java.util.Date;
import java.util.List;

public interface SettingsDAO {
	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, Date deadline,Boolean freeze, Integer SettingId);
	public List<Double> getMoney(Integer settingsId);
}

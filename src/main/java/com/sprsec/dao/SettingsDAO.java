package com.sprsec.dao;

import java.util.Date;

public interface SettingsDAO {
	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, Date deadline,Boolean freeze, Integer SettingId);
}
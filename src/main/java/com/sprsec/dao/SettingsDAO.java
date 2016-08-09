package com.sprsec.dao;

import java.util.Date;
import java.util.List;

public interface SettingsDAO {


	public List<Double> getMoney(Integer settingsId);

	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, String deadline,Boolean freeze, Integer settings_id);
}

package com.sprsec.service;

import java.util.Date;
import java.util.List;

public interface SettingService {
	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, Date deadline,Boolean freeze, Integer SettingId);
	public List<Double> getMoney(Integer settingsId);

}

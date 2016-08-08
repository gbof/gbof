package com.sprsec.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprsec.dao.SettingsDAO;

@Service
@Transactional
public class SettingServiceImpl implements SettingService{
	
	@Autowired
	private SettingsDAO settingsDAO;
	
	public void addSetting(Integer extraBalls, Integer balls_per_pers, Double money, Date deadline,Boolean freeze, Integer SettingId)
	{
		settingsDAO.addSetting(extraBalls, balls_per_pers, money, deadline, freeze, SettingId);
	}
}

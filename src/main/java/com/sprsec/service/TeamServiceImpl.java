package com.sprsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprsec.dao.TeamDAO;
import com.sprsec.model.Team;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamDAO teamDAO;
	
	@Override
	public List<Team> getAllTeams() {
		return teamDAO.getAllTeams();
	}

	@Override
	public Team getTeamID(String name) {
		return teamDAO.getTeamID(name);
	}

}

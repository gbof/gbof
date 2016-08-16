package com.ericsson.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.dao.TeamDAO;
import com.ericsson.model.Team;

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

	@Override
	public void addTeam(String teamName, Integer leaderID, Integer deptID) {
		teamDAO.addTeam(teamName, leaderID, deptID);
		
	}

	@Override
	public void removeTeam(Integer team_id) {
		teamDAO.removeTeam(team_id);
		
	}

}
